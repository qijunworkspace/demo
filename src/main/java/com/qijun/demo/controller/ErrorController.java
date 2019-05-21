package com.qijun.demo.controller;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Controller;

/**
 * 用于配置发生错误时的页面跳转  ErrorViewResolver
 * @author Qijun
 * @date 11/28/18 11:36 AM
 * @version 1.0
 */
//@Controller
@Deprecated
public class ErrorController extends AbstractErrorController{


    public ErrorController(){
        super(new DefaultErrorAttributes());
    }


    @Override
    public String getErrorPath() {
        return null;
    }
}
