package com.qijun.demo.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RedisConfig
 * @Description 配置Redis缓存
 * @Author Qijun
 * @Date 11/29/18 4:23 PM
 * @Version 1.0
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {


}
