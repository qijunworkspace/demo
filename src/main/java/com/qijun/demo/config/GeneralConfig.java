package com.qijun.demo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * 通用自定义配置
 * @author Qijun
 * @date 11/28/18 2:13 PM
 * @version 1.0
 */
@Configuration
public class GeneralConfig {

    /**
     * Springboot默认使用Jackson实现序列化
     * 自定序列化格式, 返回JSON时自动应用
     * @return 对象序列化器
     */
    @Bean
    public ObjectMapper getObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //设置默认的时间序列化格式
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
        //设置启用默认类型，排除final属性字段
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        return mapper;
    }
}
