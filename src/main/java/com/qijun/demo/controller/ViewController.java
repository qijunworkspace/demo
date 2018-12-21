package com.qijun.demo.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 视图跳转控制器
 * @author Qijun
 * @date 11/23/18 3:17 PM
 * @version 1.0
 */
@Controller
@RequestMapping("/view")
public class ViewController {

    private Log log = LogFactory.getLog(ViewController.class);

    /**
     * 跳转登录页面
     * @return path
     */
    @RequestMapping("login")
    public String login(){
        return "login";
    }

    /**
     * 跳转主页面
     * @return path
     */
    @RequestMapping("/home")
    public String home(){
        return "components/home";
    }

    /**
     * 跳转个人信息页面
     * @return path
     */
    @RequestMapping("/userProfile")
    public String userProfile(){
        return "components/user-profile";
    }

}
