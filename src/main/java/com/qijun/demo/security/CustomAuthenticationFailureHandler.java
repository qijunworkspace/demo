package com.qijun.demo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qijun.demo.util.ResponseUtil;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义验证失败处理器
 * 返回错误信息
 * @author Qijun
 * @version 1.0
 * @date 12/25/18 8:07 PM
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Called when an authentication attempt fails.
     *
     * @param request   the request during which the authentication attempt occurred.
     * @param response  the response.
     * @param exception the exception which was thrown to reject the authentication
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException{

        String message = exception.getMessage();
        //判断异常类型
        if (exception instanceof UsernameNotFoundException){
            message = "Unknown username!";
        }else if (exception instanceof DisabledException){
            message = "Your account is disabled!";
        }else if (exception instanceof AccountExpiredException){
            message = "Your account is expired!";
        }else if (exception instanceof LockedException){
            message = "Your account is locked!";
        }else if (exception instanceof CredentialsExpiredException){
            message = "Your account's credential is expired!";
        }else if (exception instanceof InsufficientAuthenticationException){
            message = "Operation denied: " + exception.getMessage();
        }else if (exception instanceof  BadCredentialsException){
            message = "Unknown username or incorrect password";
        }

        //判断是否是直接登录失败
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(objectMapper.writeValueAsString(ResponseUtil.error(message)));
        out.flush();
        out.close();
    }
}
