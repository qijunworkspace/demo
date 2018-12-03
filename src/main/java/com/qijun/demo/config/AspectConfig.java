package com.qijun.demo.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName AspectConfig
 * @Description 定义系统使用的切点及切面
 *
 *      @Pointcut: 表示一组切点，定义了Advice（建言）将要发生的地方
 *          | arg(): 参数为指定类型的执行方法
 *          | @args(): 参数由指定注解标注的执行方法
 *          | execution(): 用于匹配连接点的执行方法
 *          | this(): 限制连接点匹配 AOP 代理的 Bean 引用为指定类型的类
 *          | target(): 限制连接点匹配特定的执行对象，这些对象对应的类要具备指定类型注解
 *          | within(): 限制连接点匹配指定类型
 *          | @within(): 限制连接点匹配指定注释所标注的类型
 *          | @annotation(): 限制匹配带有指定注释的连接点
 *      Advice:
 *          | @Before
 *          | @Around
 *          | @After
 *          | @Finally
 *
 * @Author Qijun
 * @Date 11/26/18 3:54 PM
 * @Version 1.0
 */
@Aspect
@Configuration
public class AspectConfig {

}
