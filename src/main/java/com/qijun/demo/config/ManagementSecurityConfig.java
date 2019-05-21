package com.qijun.demo.config;

import com.qijun.demo.common.GlobalConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementServerProperties;
import org.springframework.boot.actuate.context.ShutdownEndpoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 解决actuator的访问权限问题
 * 不能直接使用SecurityConfig的配置, 在监控页面使用basic验证方式
 * @author Qijun
 * @version 1.0
 * @date 12/28/18 8:25 AM
 */
@Order(0)
@Configuration
public class ManagementSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    ManagementServerProperties managementProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .requestMatchers((request) -> managementProperties.getPort().equals(request.getServerPort()))
                .and()
                .authorizeRequests()
                .requestMatchers(EndpointRequest.to(ShutdownEndpoint.class)).hasRole(GlobalConstants.SUPER_ADMIN)
                .requestMatchers(EndpointRequest.toAnyEndpoint()).authenticated()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }
}
