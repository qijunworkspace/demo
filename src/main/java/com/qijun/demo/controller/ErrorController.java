package com.qijun.demo.controller;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Controller;

/**
 * @ClassName ErrorController
 * @Description {配置需要确认}
 * @Author Qijun
 * @Date 11/28/18 11:36 AM
 * @Version 1.0
 */
@Controller
public class ErrorController extends AbstractErrorController{


    public ErrorController(){
        super(new DefaultErrorAttributes());
    }


    @Override
    public String getErrorPath() {
        return null;
    }
}
