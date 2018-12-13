package com.qijun.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qijun.demo.config.props.RedisProperties;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @ClassName RedisConfig
 * @Description 配置Redis缓存
 * @Author Qijun
 * @Date 11/29/18 4:23 PM
 * @Version 1.0
 */
@Configuration
public class RedisConfig{

    @Autowired
    private RedisProperties redisProperties;
    @Autowired
    private ObjectMapper objectMapper;


    /**
     * Lettuce客户端连接池配置(覆盖SpringBoot默认配置)
     * @return
     */
    @Bean
    public LettuceClientConfiguration lettuceClientConfiguration(){
        //设置通用连接池配置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(redisProperties.getLettuce().getPool().getMaxIdle());
        poolConfig.setMinIdle(redisProperties.getLettuce().getPool().getMinIdle());
        poolConfig.setMaxTotal(redisProperties.getLettuce().getPool().getMaxActive());
        poolConfig.setMaxWaitMillis(redisProperties.getLettuce().getPool().getMaxWait().toMillis());
        poolConfig.setBlockWhenExhausted(true);
        //生成Lettuce连接池客户端配置
        LettuceClientConfiguration lettucePoolingClientConfiguration =
                LettucePoolingClientConfiguration.builder().commandTimeout(redisProperties.getTimeout()).poolConfig(poolConfig).build();

        return lettucePoolingClientConfiguration;
    }

    /**
     * 配置Redis连接工厂(覆盖SpringBoot默认配置)
     * @param lettuceClientConfiguration
     * @return
     */
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(LettuceClientConfiguration lettuceClientConfiguration){
        //单机配置
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setDatabase(redisProperties.getDatabase());
        standaloneConfiguration.setHostName(redisProperties.getHost());
        standaloneConfiguration.setPassword(redisProperties.getPassword());
        standaloneConfiguration.setPort(redisProperties.getPort());

        // 集群配置
        // RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        // String[] serverArray = clusterNodes.split(",");
        // Set<RedisNode> nodes = new HashSet<RedisNode>();
        // for (String ipPort : serverArray) {
        //    String[] ipAndPort = ipPort.split(":");
        //    nodes.add(new RedisNode(ipAndPort[0].trim(), Integer.valueOf(ipAndPort[1])));
        // }
        // redisClusterConfiguration.setPassword(RedisPassword.of(password));
        // redisClusterConfiguration.setClusterNodes(nodes);
        // redisClusterConfiguration.setMaxRedirects(maxRedirects);

        //创建连接工厂
        LettuceConnectionFactory factory = new LettuceConnectionFactory(standaloneConfiguration, lettuceClientConfiguration);
        factory.setValidateConnection(true);
        factory.afterPropertiesSet();
        factory.initConnection();

        return factory;
    }

    /**
     * 配置直接操作缓存的redisTemplate对象
     * @param lettuceConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory){
        // 键序列化
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
        // 值序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        RedisTemplate<String, Object> redisTemplate =  new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        // 开启事务支持
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

}
