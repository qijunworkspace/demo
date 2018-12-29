package com.qijun.demo.security;

import com.qijun.demo.common.GlobalConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 缺少访问权限时
 * 跳转未授权页面
 * @author Qijun
 * @version 1.0
 * @date 12/29/18 10:57 AM
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Handles an access denied failure.
     *
     * @param request               that resulted in an <code>AccessDeniedException</code>
     * @param response              so that the user agent can be advised of the failure
     * @param accessDeniedException that caused the invocation
     * @throws IOException      in the event of an IOException
     * @throws ServletException in the event of a ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        logger.warn("CustomAccessDeniedHandler processed!");

        //判断是否已经提交(设置过返回值)
        if (!response.isCommitted()) {
            // Put exception into request scope (perhaps of use to a view)
            request.setAttribute(WebAttributes.ACCESS_DENIED_403, accessDeniedException);
            // Set the 403 status code.
            response.setStatus(HttpStatus.FORBIDDEN.value());
            // forward to error page.
            RequestDispatcher dispatcher = request.getRequestDispatcher(GlobalConstants.UNAUTHORIZED_PAGE);
            dispatcher.forward(request, response);
        }
    }
}
