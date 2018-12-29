package com.qijun.demo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qijun.demo.common.GlobalConstants;
import com.qijun.demo.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 判断验证码是否正确
 *
 * 1、继承 OncePerRequestFilter 将导致每一次访问都过滤，需要在登录后设置一次免验证(存在问题:拦截器不能捕获抛出异常,需要实现返回)
 * 2、继承 AbstractAuthenticationProcessingFilter 将会替换原有的 UsernamePasswordAuthenticationFilter, 需要实现所有逻辑
 * 3、继承 UsernamePasswordAuthenticationFilter，改写验证部分，需要手动注入failure和success处理器、验证处理器，loginForm接口的不会自动应用
 *
 * @author Qijun
 * @version 1.0
 * @date 12/28/18 7:18 PM
 */
@Component
public class KaptchaValidationFilter extends OncePerRequestFilter {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        //判断是否是登录请求
       if (HttpMethod.POST.name().equalsIgnoreCase(request.getMethod())
               && request.getRequestURI().equalsIgnoreCase(GlobalConstants.LOGIN_PROCESS_URL)) {

           String errorMsg = null;
           //判断验证码是否正确
           String inputKaptcha = request.getParameter(GlobalConstants.KAPTCHA_SESSION_KEY);
           Object serverKaptchaObject = request.getSession().getAttribute(GlobalConstants.KAPTCHA_SESSION_KEY);
           if (StringUtils.isEmpty(inputKaptcha)){
               errorMsg = "Verification is required!";
           }else if (null == serverKaptchaObject){
               errorMsg = "Verification is expired!";
           }else{
               String serverKaptcha = (String) serverKaptchaObject;
               //不区分大小写
               if (!serverKaptcha.equalsIgnoreCase(inputKaptcha)){
                   errorMsg = "Verification incorrect!";
               }
           }

           //判断是否直接返回错误信息
           if (!StringUtils.isEmpty(errorMsg)){
               response.setContentType("application/json;charset=utf-8");
               PrintWriter out = response.getWriter();
               out.write(objectMapper.writeValueAsString(ResponseUtil.error(errorMsg)));
               out.flush();
               out.close();
               return;
           }
           //验证成功，session删除验证码
           request.getSession().removeAttribute(GlobalConstants.KAPTCHA_SESSION_KEY);
       }
       filterChain.doFilter(request, response);
    }
}
