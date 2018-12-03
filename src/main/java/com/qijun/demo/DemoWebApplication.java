package com.qijun.demo;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @ClassName DemoWebApplication
 * @Description 部署在web容器中时必须设置
 * @Author Qijun
 * @Date 11/23/18 3:17 PM
 * @Version 1.0
 */
public class DemoWebApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DemoApplication.class);
    }
}
