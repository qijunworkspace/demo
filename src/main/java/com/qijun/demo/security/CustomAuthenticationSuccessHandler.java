package com.qijun.demo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qijun.demo.common.GlobalConstants;
import com.qijun.demo.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义验证成功处理器
 * 可以自定义返回或跳转页面
 * @author Qijun
 * @version 1.0
 * @date 12/25/18 8:08 PM
 */
@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 请求对象的缓存
     */
    private RequestCache requestCache = new HttpSessionRequestCache();

    /**
     * Called when a user has been successfully authenticated.
     *
     * @param request        the request which caused the successful authentication
     * @param response       the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        String forwardPath = request.getContextPath() + GlobalConstants.HOME_PAGE;

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (null != savedRequest){
            forwardPath = savedRequest.getRedirectUrl();
            logger.info("SavedRequest found:" + forwardPath);
            clearAuthenticationAttributes(request);
        }

        //返回跳转主页消息
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(objectMapper.writeValueAsString(ResponseUtil.success(forwardPath)));
        out.flush();
        out.close();
    }
}
