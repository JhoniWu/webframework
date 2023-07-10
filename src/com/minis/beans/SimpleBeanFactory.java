package com.minis.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: MicroSpring
 * @description:
 * @author: Max Wu
 * @create: 2023-07-10 16:31
 **/
public class SimpleBeanFactory implements BeanFactory{
	private List<BeanDefinition> beanDefinitions = new ArrayList<>();
	private List<String> beanNames = new ArrayList<>();
	private Map<String, Object> singletons = new HashMap<>();
	public SimpleBeanFactory(){}
	//获取Bean，核心方法
	@Override
	public Object getBean(String beanName) throws BeansException {
		Object singleton = singletons.get(beanName);
		if(singleton == null) {
			int i = beanNames.indexOf(beanName);
			if(i == -1) {
				throw new BeansException("无此beanName");
			} else {
				BeanDefinition beanDefinition = beanDefinitions.get(i);
				try {
					singleton = Class.forName(beanDefinition.getClassName()).newInstance();
				} catch (ClassNotFoundException e) {
					throw new RuntimeException(e);
				} catch (InstantiationException e) {
					throw new RuntimeException(e);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
				singletons.put(beanDefinition.getId(), singleton);
			}
		}
		return singleton;
	}

	@Override
	public void registerBeanDefinition(BeanDefinition beanDefinition) {
		this.beanDefinitions.add(beanDefinition);
		this.beanNames.add(beanDefinition.getId());
	}
}
