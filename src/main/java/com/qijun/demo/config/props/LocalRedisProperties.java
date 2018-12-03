package com.qijun.demo.config.props;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @ClassName LocalRedisProperties
 * @Description 为了将redis的配置文件独立出来, 设置本地类
 * @Author Qijun
 * @Date 11/29/18 5:03 PM
 * @Version 1.0
 */
@PropertySource(value = "classpath:config/redis.properties")
@ConfigurationProperties(prefix = "spring.redis")
public class LocalRedisProperties extends RedisProperties {

    /**
     * 可以添加参数, 对应配置文件
     */
}
