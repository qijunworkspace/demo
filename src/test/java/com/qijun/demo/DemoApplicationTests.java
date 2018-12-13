package com.qijun.demo;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Spring单元测试并不会在每个测试方法前都启动新的Spring上下文
 * 可以通过@DirtiesContext提示重新加载上下文
 *
 * 必须指明springboot启动环境RANDOM_PORT，否则提示ES netty错误
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoApplication.class}, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoApplicationTests {

	@BeforeClass
	public static void init(){
		System.setProperty("es.set.netty.runtime.available.processors", "false");
	}

	@LocalServerPort
	private int port;

	@Before
	public void setUp(){
		System.out.println("Current Test port: " + port);
	}

	@Autowired
	private DruidDataSource config;

	@Test
	public void contextLoads() {
		System.out.println("This is a test!");
	}

}

