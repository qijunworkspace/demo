package com.qijun.demo.common;

/**
 * 系统使用的常量集合
 *
 * @author Qijun
 * @version 1.0
 * @date 12/26/18 4:07 PM
 */
public class GlobalConstants {
    /**
     * 登录页面路径
     */
    public static final String LOGIN_PAGE = "/login";

    /**
     * 登录验证路径
     */
    public static final String LOGIN_PROCESS_URL = "/auth/login";

    /**
     * 获取验证码的路径
     */
    public static final String LOGIN_KAPTHCA = "/auth/kaptcha";

    /**
     * 退出系统路径
     */
    public static final String LOGOUT_PROCESS_URL = "/auth/logout";

    /**
     * 登录注册路径
     */
    public static final String LOGIN_REGISTRATION_URL = "/auth/registration";

    /**
     * 登录成功后显示的主页面
     */
    public static final String HOME_PAGE = "/home";

    /**
     * 未授权页面
     */
    public static final String UNAUTHORIZED_PAGE = "/unauthorized";

    /**
     * 通用错误页面
     */
    public static final String ERROR_PAGE = "/error";

    /**
     * 404错误页面
     */
    public static final String ERROR_404_PAGE = "/error-404";

    /**
     * 默认记住我的时间为7日
     */
    public static final Integer REMEMBER_ME_VALIDITY_SECONDS = 7*24*3600;


    /**
     * 超级管理员角色名称
     */
    public static final String SUPER_ADMIN = "SUPER_ADMIN";

    /**
     * 验证码存储对应的键
     */
    public static final String KAPTCHA_SESSION_KEY = "verification";
}
