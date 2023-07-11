package com.minis.beans;

/**
 * @program: MicroSpring
 * @description:
 * @author: Max Wu
 * @create: 2023-07-11 13:49
 **/
public interface BeanDefinitionRegistry {
	void registerBeanDefinition(String name, BeanDefinition bd);
	void removeBeanDefinition(String name);
	BeanDefinition getBeanDefinition(String name);
	boolean containsBeanDefinition(String name);
}
