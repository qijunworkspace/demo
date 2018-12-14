package com.qijun.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 主类
 * SpringBootApplication 开启自动扫描和组件配置
 * 等价于开启 @Configuration @ComponentScan @EnableAutoConfiguration
 * @author Qijun
 * @date 12/13/18 4:33 PM
 * @version 1.0
 */
@SpringBootApplication
@MapperScan("com.qijun.demo.mapper")
public class DemoApplication {

	public static void main(String[] args) {
		//Springboot整合Elasticsearch 在项目启动前设置一下的属性，防止报错
		System.setProperty("es.set.netty.runtime.available.processors", "false");

	    //启动引导程序
		SpringApplication.run(DemoApplication.class, args);
	}
}
