package com.qijun.demo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.qijun.demo.common.GlobalConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Properties;

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
     *
     * mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
     * 将影响全局返回JSON，结果生成包含类名的数组
     *
     * @return 对象序列化器
     */
    @Bean
    public ObjectMapper getObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        //设置默认的时间序列化格式
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
        return mapper;
    }

    /**
     * 验证码配置类
     * @return kaptcha
     */
    @Bean
    public DefaultKaptcha defaultKaptcha(){
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Properties props = new Properties();
        // 图片边框
        props.setProperty(Constants.KAPTCHA_BORDER, "yes");
        // 边框颜色
        props.setProperty(Constants.KAPTCHA_BORDER_COLOR, "105,179,90");
        // 字体颜色
        props.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "BLUE");
        //噪声颜色
        props.setProperty(Constants.KAPTCHA_NOISE_COLOR,"pink");
        // 图片宽
        props.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, "145");
        // 图片高
        props.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, "40");
        // 字体大小
        props.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, "30");
        // session key
        props.setProperty(Constants.KAPTCHA_SESSION_KEY, GlobalConstants.KAPTCHA_SESSION_KEY);
        //设置生成的文本内容: 数字加大写字母，去除0/O 1/I
        props.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, "23456789QWERTYUPLKJHGFDSAMNBVCXZ");
        // 验证码长度
        props.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
        // 字体
        props.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, "Microsoft YaHei");

        Config config = new Config(props);
        kaptcha.setConfig(config);
        return kaptcha;
    }
}
