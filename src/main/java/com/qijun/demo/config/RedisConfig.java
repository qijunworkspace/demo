package com.qijun.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qijun.demo.config.props.RedisProperties;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 配置Redis缓存
 * EnableRedisHttpSession将生成bean: springSessionRepositoryFilter
 * 该bean替换HttpSession为SpringSession
 * @author Qijun
 * @date 11/29/18 4:23 PM
 * @version 1.0
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 24*3600*7)
public class RedisConfig{

    private RedisProperties redisProperties;
    private ObjectMapper objectMapper;

    @Autowired
    public RedisConfig(RedisProperties redisProperties, ObjectMapper objectMapper){
        this.redisProperties = redisProperties;
        this.objectMapper = objectMapper;
    }

    /**
     * Lettuce客户端连接池配置(覆盖SpringBoot默认配置)
     * @return lettuceClientConfiguration
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
        return LettucePoolingClientConfiguration.builder().commandTimeout(redisProperties.getTimeout()).poolConfig(poolConfig).build();
    }

    /**
     * 配置Redis连接工厂(覆盖SpringBoot默认配置)
     * @param lettuceClientConfiguration 客户端配置
     * @return lettuceConnectionFactory
     */
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(LettuceClientConfiguration lettuceClientConfiguration){
        //单机配置
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setDatabase(redisProperties.getDatabase());
        standaloneConfiguration.setHostName(redisProperties.getHost());
        standaloneConfiguration.setPassword(redisProperties.getPassword());
        standaloneConfiguration.setPort(redisProperties.getPort());

        //创建连接工厂
        LettuceConnectionFactory factory = new LettuceConnectionFactory(standaloneConfiguration, lettuceClientConfiguration);
        factory.setValidateConnection(true);
        factory.afterPropertiesSet();
        factory.initConnection();

        return factory;
    }

    /**
     * 配置直接操作缓存的redisTemplate对象
     * @param lettuceConnectionFactory 连接工厂
     * @return redisTemplate
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
        //redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        // 开启事务支持
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

/*
    由于采用了热部署，非延迟加载的User.ROLE反序列化失败 导致session反序列化出现问题
*/

}
