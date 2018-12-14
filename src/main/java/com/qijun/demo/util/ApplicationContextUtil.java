package com.qijun.demo.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 * 用于获取容器内的Bean
 * @date Aug 27, 2018 8:49:54 AM
 * @author qijun
 * @version 1.0
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
	 * @param name 实例名
	 * @return 对象
	 */
	public static Object getBean(String name){
		return applicationContext.getBean(name);
	}
	
	/**
	 * 按照类型获取
	 * @param cls 实例类型
	 * @return 对象
	 */
	public static <T> T getBean(Class<T> cls){
		return applicationContext.getBean(cls);
	}

}
