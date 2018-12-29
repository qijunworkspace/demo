package com.qijun.demo.controller;

import com.google.code.kaptcha.Producer;
import com.qijun.demo.common.GlobalConstants;
import com.qijun.demo.model.User;
import com.qijun.demo.response.CustomException;
import com.qijun.demo.response.CustomResponse;
import com.qijun.demo.service.UserService;
import com.qijun.demo.util.ResponseUtil;
import com.qijun.demo.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

/**
 * 登录登出控制
 * @author Qijun
 * @date 12/14/18 2:35 PM
 * @version 1.0
 */
@Controller
@RequestMapping(value="/auth")
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private UserService userService;
    private Producer kaptchaProducer;

    @Autowired
    public LoginController(UserService userService, Producer kaptchaProducer) {
        this.userService = userService;
        this.kaptchaProducer = kaptchaProducer;
    }

    /**
     * 获取验证码图形并设置在当前session中
     * @param request 请求对象
     * @param response 响应对象
     */
    @GetMapping("/kaptcha")
    public void generateKaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置返回格式
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        //生成文本，存储在session中
        String kaptcha = kaptchaProducer.createText();
        request.getSession().setAttribute(GlobalConstants.KAPTCHA_SESSION_KEY, kaptcha);
        //生成图片，传送至前台
        BufferedImage bufferedImage = kaptchaProducer.createImage(kaptcha);

        try (ServletOutputStream outputStream = response.getOutputStream()){
            ImageIO.write(bufferedImage, "jpg", outputStream);
            outputStream.flush();
        }
    }

    /**
     * 提交用户注册
     * @param user 用户信息
     * @return 注册结果
     */
    @PostMapping("/registration")
    @ResponseBody
    public CustomResponse userRegistration(@ModelAttribute @Validated User user) throws CustomException {

        //判断用户名是否存在
        if (null != userService.findByUsername(user.getUsername())){
            throw new CustomException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        //补充用户信息，默认状态为待审核
        user.setPassword(SecurityUtil.encryptAES(user.getPassword().getBytes()));
        user.setStatus(0);
        user.setRegistTime(new Date());
        //保存对象
        userService.insert(user);

        logger.info("New user registed, username = "+user.getUsername());
        return ResponseUtil.success("Registration succeed, waiting for manual review of Administrator");
    }

}
