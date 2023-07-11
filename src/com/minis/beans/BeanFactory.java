package com.minis.beans;

/**
 * @program: MicroSpring
 * @description:
 * @author: Max Wu
 * @create: 2023-07-10 16:12
 **/
public interface BeanFactory {
	Object getBean(String beanName) throws BeansException;
	//void registerBean(String name, Object obj);
	Boolean containsBeanDefinition(String name);
	boolean isSingleton(String name);
	boolean isPrototype(String name);
	Class<?> getType(String name);
}