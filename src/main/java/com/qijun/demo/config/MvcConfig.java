package com.qijun.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName MvcConfig
 * @Description 全局定制MVC特性(实现接口定义)
 * @Author Qijun
 * @Date 11/27/18 2:04 PM
 * @Version 1.0
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry){
    }

    /**
     * 跨域访问配置
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                // 允许跨域请求中携带cookie
                .allowCredentials(true)
                .allowedHeaders(CorsConfiguration.ALL)
                .allowedMethods(CorsConfiguration.ALL)
                .allowedOrigins(CorsConfiguration.ALL);
    }

    /**
     * 添加格式化工具
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry){
    }

    /**
     * 添加静态资源处理器
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        //添加swagger静态资源的支持
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/")
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic());
        registry.addResourceHandler("/swagger*")
                .addResourceLocations("classpath:/META-INF/resources/")
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic());
        registry.addResourceHandler("/v2*")
                .addResourceLocations("classpath:/META-INF/resources/")
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic());
        registry.addResourceHandler("/webjars*")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic());
    }

    /**
     * 添加URI到视图的映射
     * 可从controller中设置返回
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        //设置主页(默认可不设置)
        registry.addViewController("/").setViewName("index.html");
    }

    /**
     * 扩展异常处理器
     * 可添加异常对应跳转视图
     * @param resolvers
     */
    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers){
    }
}
