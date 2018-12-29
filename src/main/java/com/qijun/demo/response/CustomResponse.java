package com.qijun.demo.response;

import org.springframework.http.HttpStatus;

/**
 * 统一的返回结果
 *
 * @author Qijun
 * @version 1.0
 * @date 12/21/18 4:01 PM
 */
public class CustomResponse {

    /**
     * 返回状态码
     */
    private HttpStatus code;

    /**
     * 返回消息内容
     */
    private String message;

    /**
     * 返回对象数据
     */
    private Object data;


    public CustomResponse(HttpStatus code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code.value();
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
