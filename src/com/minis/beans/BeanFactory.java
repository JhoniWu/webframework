package com.minis.beans;

/**
 * @program: MicroSpring
 * @description: 读取bean配置文档，管理bean的加载，实例化，维护bean之间的依赖关系
 * @author: Max Wu
 * @create: 2023-07-10 16:12
 **/
public interface BeanFactory {
	Object getBean(String name) throws BeansException;

	boolean containsBean(String name);

	//void registerBean(String beanName, Object obj);
	boolean isSingleton(String name);

	boolean isPrototype(String name);

	Class<?> getType(String name);

}