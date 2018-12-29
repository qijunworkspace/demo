package com.qijun.demo.common;

import com.qijun.demo.response.CustomException;
import com.qijun.demo.response.CustomResponse;
import com.qijun.demo.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 全局异常处理器切片
 *
 * @author Qijun
 * @version 1.0
 * @date 12/25/18 11:33 AM
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理自定义异常
     * @param request 请求对象
     * @param response 相应对象
     * @param exception 自定义异常
     * @return 统一返回结果
     */
    @ExceptionHandler(CustomException.class)
    public CustomResponse customException(HttpServletRequest request, HttpServletResponse response, CustomException exception){
        logger.error(exception.toString());
        return ResponseUtil.build(exception.getCode(), exception.getMessage(), null);
    }


    /**
     * 参数绑定异常
     * @param exception 参数绑定错误
     * @return 统一返回结果(406)
     */
    @ExceptionHandler(BindException.class)
    public CustomResponse bindException(BindException exception){
        logger.error("BindException: " + exception.getBindingResult().getAllErrors());
        List<String> errors = new ArrayList<>();
        for (ObjectError error: exception.getBindingResult().getAllErrors()){
            errors.add(error.getDefaultMessage());
        }
        return ResponseUtil.error(errors);
    }

    /**
     * Exception to be thrown when validation on an argument annotated with {@code @Valid} fails.
     * @param exception 方法参数异常
     * @return 统一返回结果(400)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CustomResponse methodArgumentNotValidException(MethodArgumentNotValidException exception){
        logger.error("MethodArgumentNotValidException: " + exception.getBindingResult().getAllErrors());
        List<String> errors = new ArrayList<>();
        for (ObjectError error: exception.getBindingResult().getAllErrors()){
            errors.add(error.getDefaultMessage());
        }
        return ResponseUtil.error(errors);
    }


    /**
     * 默认异常处理
     * @param exception 异常对象
     * @return 统一返回结果(417)
     */
    @ExceptionHandler({Exception.class})
    public CustomResponse defaultException(HttpServletRequest request, Exception exception){
        logger.error("Exception: "+request.getRequestURI()+" - "+exception.getMessage());
        exception.printStackTrace();
        return ResponseUtil.error(exception.getMessage());
    }

}
