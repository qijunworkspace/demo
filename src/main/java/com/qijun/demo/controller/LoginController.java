package com.qijun.demo.controller;

import com.qijun.demo.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录登出控制
 * @author Qijun
 * @date 12/14/18 2:35 PM
 * @version 1.0
 */
@RestController
@RequestMapping
public class LoginController {

    private Log log = LogFactory.getLog(LoginController.class);

    @Autowired
    private UserService userService;


}
