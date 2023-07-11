package com.minis.beans;

import javax.naming.NamingEnumeration;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: MicroSpring
 * @description:
 * @author: Max Wu
 * @create: 2023-07-10 16:31
 **/
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory{
	private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
	private List<String> beanDefinitionNames = new ArrayList<>();
	public SimpleBeanFactory(){
	}
	//获取Bean，核心方法
	@Override
	public Object getBean(String beanName) throws BeansException {
		Object singleton = singletons.get(beanName);
		if(singleton == null) {
			BeanDefinition definition = beanDefinitionMap.get(beanName);
			if(definition == null) {
				throw new BeansException("No beans.");
			}

			try {
				singleton = Class.forName(definition.getClassName()).newInstance();
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			this.registerSingleton(beanName, singleton);
		}
		return singleton;
	}

	public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
		this.beanDefinitionMap.put(name, beanDefinition);
		this.beanDefinitionNames.add(name);
		if(!beanDefinition.isLazyInit()){
			try {
				getBean(name);
			} catch (BeansException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public BeanDefinition getBeanDefinition(String name){
		return this.beanDefinitionMap.get(name);
	}

	public void removeBeanDefinition(String name) {
		this.beanDefinitionMap.remove(name);
		this.beanDefinitionNames.remove(name);
		this.removeSingleton(name);
	}

	@Override
	public Boolean containsBeanDefinition(String name) {
		return this.beanDefinitionMap.containsKey(name);
	}

	@Override
	public boolean isSingleton(String name) {
		return this.beanDefinitionMap.get(name).isSingleton();
	}

	@Override
	public boolean isPrototype(String name) {
		return this.beanDefinitionMap.get(name).isPrototype();
	}

	@Override
	public Class<?> getType(String name) {
		return this.beanDefinitionMap.get(name).getClass();
	}

	public void registerBean(String beanName, Object obj){
		this.registerSingleton(beanName, obj);
	}



}
