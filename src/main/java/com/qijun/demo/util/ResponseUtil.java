package com.qijun.demo.util;

import com.qijun.demo.response.CustomResponse;
import org.springframework.http.HttpStatus;

/**
 * 返回结果工具类
 *
 * @author Qijun
 * @version 1.0
 * @date 12/21/18 4:51 PM
 */
public class ResponseUtil {

    /**
     * 默认成功返回消息内容
     */
    public static final String DEFAULT_SUCCESS = "success";

    /**
     * 默认失败返回消息内容
     */
    public static final String DEFAULT_ERROR = "error";

    /**
     * 返回成功消息数据
     * @param data 对象
     * @return CustomResponse
     */
    public static CustomResponse success(Object data){
        return new CustomResponse(HttpStatus.OK, DEFAULT_SUCCESS, data);
    }

    /**
     * 返回成功提示消息
     * @param message 提示消息
     * @return CustomResponse
     */
    public static CustomResponse success(String message){
        return new CustomResponse(HttpStatus.OK, message, null);
    }


    /**
     * 异常失败返回消息
     * @param message 提示消息
     * @return CustomResponse
     */
    public static CustomResponse error(String message){
        return new CustomResponse(HttpStatus.EXPECTATION_FAILED, message, null);
    }


    /**
     * 异常失败返回消息
     * @param data 返回对象数据
     * @return CustomResponse
     */
    public static CustomResponse error(Object data){
        return new CustomResponse(HttpStatus.EXPECTATION_FAILED, DEFAULT_ERROR, data);
    }

    /**
     * 自定义返回
     * @param status 状态码
     * @param message 提示消息
     * @param data 对象
     * @return CustomResponse
     */
    public static CustomResponse build(HttpStatus status, String message, Object data){
        return new CustomResponse(status, message, data);
    }

}
