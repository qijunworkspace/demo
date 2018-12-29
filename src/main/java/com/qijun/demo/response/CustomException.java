package com.qijun.demo.response;

import org.springframework.http.HttpStatus;

/**
 * 自定义抛出异常格式
 *
 * @author Qijun
 * @version 1.0
 * @date 12/21/18 4:40 PM
 */
public class CustomException extends RuntimeException{

    /**
     * 异常代码
     */
    private HttpStatus code;

    /**
     * 异常消息提示内容
     */
    private String message;



    public CustomException(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }


    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CustomException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
