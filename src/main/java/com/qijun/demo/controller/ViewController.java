package com.qijun.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 视图跳转控制器
 * @author Qijun
 * @date 11/23/18 3:17 PM
 * @version 1.0
 */
@Controller
@RequestMapping
public class ViewController {

    /**
     * 跳转登录页面
     * @return path
     */
    @GetMapping("/login")
    public String login(HttpServletRequest request){
        return "login";
    }

    /**
     * 跳转未授权页面
     * @return path
     */
    @GetMapping("/unauthorized")
    public String unauthorized(){
        return "unauthorized";
    }

    /**
     * 跳转错误页面
     * @return path
     */
    @GetMapping("/error")
    public String error(){
        return "error";
    }

    /**
     * 跳转404错误页面
     * @return path
     */
    @GetMapping("/error-404")
    public String error404(){
        return "error-404";
    }

    /**
     * 跳转主页面
     * @return path
     */
    @GetMapping({"/home","/"})
    public String home(HttpServletRequest request){
        return "components/home";
    }

    /**
     * 跳转个人信息页面
     * @return path
     */
    @GetMapping("/userProfile")
    public String userProfile(){
        return "components/user-profile";
    }

}
