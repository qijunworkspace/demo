package com.qijun.demo.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 * @ClassName ApplicationContextUtil.java
 * @Description 用于获取容器内的Bean
 * @Date Aug 27, 2018 8:49:54 AM
 * @author qijun
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
			ApplicationContextUtil.applicationContext = applicationContext;
	}

	/**
	 * 按照变量实例名获取
	 * @param name
	 * @return
	 */
	public static Object getBean(String name){
		return applicationContext.getBean(name);
	}
	
	/**
	 * 按照类型获取
	 * @param cls
	 * @return
	 */
	public static <T> T getBean(Class<T> cls){
		return applicationContext.getBean(cls);
	}

}
