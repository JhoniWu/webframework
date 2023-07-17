package com.minis.beans;

/**
 * @program: MicroSpring
 * @description:  单例bean注册表
 * @author: Max Wu
 * @create: 2023-07-11 08:50
 **/
public interface SingletonBeanRegistry {
	//注册bean
	void registerSingleton(String beanName, Object singletonObject);
	//获取bean
	Object getSingleton(String beanName);
	//判断存在
	boolean containsSingleton(String beanName);

	//获取所有单例bean
	String[] getSingletonNames();
}