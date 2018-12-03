package com.qijun.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//开启自动扫描和组件配置
//等价于开启 @Configuration @ComponentScan @EnableAutoConfiguration
@SpringBootApplication
//指定扫锚路径
@MapperScan("com.qijun.demo")
public class DemoApplication {

	public static void main(String[] args) {
		/**
		 * Springboot整合Elasticsearch 在项目启动前设置一下的属性，防止报错
		 * java.lang.IllegalStateException: availableProcessors is already set
		 */
		System.setProperty("es.set.netty.runtime.available.processors", "false");

	    //启动引导程序
		SpringApplication.run(DemoApplication.class, args);
	}
}
