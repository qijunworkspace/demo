package com.qijun.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;

/**
 * 配置Spring的缓存管理
 * @author Qijun
 * @date 12/13/18 11:19 AM
 * @version 1.0
 */
@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

    private LettuceConnectionFactory lettuceConnectionFactory;
    private ObjectMapper objectMapper;

    @Autowired
    public CacheConfig(LettuceConnectionFactory lettuceConnectionFactory, ObjectMapper objectMapper){
        this.lettuceConnectionFactory = lettuceConnectionFactory;
        this.objectMapper = objectMapper;
    }

    /**
     * 自定义缓存管理器(redis实现)
     * @return Springboot缓存管理器
     */
    @Override
    @Bean
    public CacheManager cacheManager() {
        // 键序列化
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        // 值序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        //配置Spring缓存属性
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        configuration = configuration.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));

        //生成含有默认配置信息的缓存管理器
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(lettuceConnectionFactory).cacheDefaults(configuration).build();
    }

    @Override
    public CacheResolver cacheResolver() {
        return super.cacheResolver();
    }

    /**
     * 未指定键值时，使用该对象生成
     * lambda只能用于函数式接口
     * @return 键生成器
     */
    @Override
    @Bean
    public KeyGenerator keyGenerator() {

        return (Object target,Method method, Object...params)->{
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
        };
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return super.errorHandler();
    }
}
