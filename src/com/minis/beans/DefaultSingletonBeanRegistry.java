package com.minis.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: MicroSpring
 * @description: 默认实现类，完成bean的单例注册
 * @author: Max Wu
 * @create: 2023-07-11 08:54
 **/
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry{
	//容器中存放所有Bean的名称和列表
	protected List<String> beanNames = new ArrayList<>();
	//容器中存放bean实列map
	protected Map<String, Object> singletons = new ConcurrentHashMap<>(256);

	/**
	 * 注册方法
	 * @param beanName
	 * @param singletonObject
	 */
	@Override
	public void registerSingleton(String beanName, Object singletonObject) {
		synchronized (this.singletons) {
			this.singletons.put(beanName, singletonObject);
			this.beanNames.add(beanName);
		}
	}

	@Override
	public Object getSingleton(String beanName) {
		return this.singletons.get(beanName);
	}

	@Override
	public boolean containsSingleton(String beanName) {
		return this.singletons.containsKey(beanName);
	}

	@Override
	public String[] getSingletonName() {
		return (String[]) this.beanNames.toArray();
	}

	protected void removeSingleton(String beanName){
		synchronized (this.singletons) {
			this.beanNames.remove(beanName);
			this.singletons.remove(beanName);
		}
	}
}
