package com.minis.beans;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: MicroSpring
 * @description: 默认实现类，完成bean的单例注册
 * @author: Max Wu
 * @create: 2023-07-11 08:54
 **/
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
	//容器中存放所有Bean的名称和列表
	protected List<String> beanNames = new ArrayList<>();
	protected Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);
	protected Map<String, Set<String>> dependentBeanMap = new ConcurrentHashMap<>(64);
	protected Map<String, Set<String>> dependenciesForBeanMap = new ConcurrentHashMap<>(64);

	/**
	 * 注册方法
	 *
	 * @param beanName
	 * @param singletonObject
	 */
	@Override
	public void registerSingleton(String beanName, Object singletonObject) {
		synchronized (this.singletonObjects) {
			Object oldObject = this.singletonObjects.get(beanName);
			if (oldObject != null) {
				throw new IllegalStateException("Could not register object [" + singletonObject +
					"] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
			}
			this.singletonObjects.put(beanName, singletonObject);
			this.beanNames.add(beanName);
			System.out.println(" bean registerded............. " + beanName);
		}
	}

	@Override
	public Object getSingleton(String beanName) {
		return this.singletonObjects.get(beanName);
	}

	@Override
	public boolean containsSingleton(String beanName) {
		return this.singletonObjects.containsKey(beanName);
	}

	@Override
	public String[] getSingletonNames() {
		return (String[]) this.beanNames.toArray();
	}


	protected void removeSingleton(String beanName) {
		synchronized (this.singletonObjects) {
			this.singletonObjects.remove(beanName);
			this.beanNames.remove(beanName);
		}
	}

	protected void registerDependentBean(String beanName, String dependentBeanName) {
		Set<String> dependentBeans = this.dependentBeanMap.get(beanName);
		if (dependentBeans != null && dependentBeans.contains(dependentBeanName)) {
			return;
		}

		// No entry yet -> fully synchronized manipulation of the dependentBeans Set
		synchronized (this.dependentBeanMap) {
			dependentBeans = this.dependentBeanMap.get(beanName);
			if (dependentBeans == null) {
				dependentBeans = new LinkedHashSet<String>(8);
				this.dependentBeanMap.put(beanName, dependentBeans);
			}
			dependentBeans.add(dependentBeanName);
		}
		synchronized (this.dependenciesForBeanMap) {
			Set<String> dependenciesForBean = this.dependenciesForBeanMap.get(dependentBeanName);
			if (dependenciesForBean == null) {
				dependenciesForBean = new LinkedHashSet<String>(8);
				this.dependenciesForBeanMap.put(dependentBeanName, dependenciesForBean);
			}
			dependenciesForBean.add(beanName);
		}
	}

	protected boolean hasDependentBean(String beanName) {
		return this.dependentBeanMap.containsKey(beanName);
	}

	protected String[] getDependentBeans(String beanName) {
		Set<String> dependentBeans = this.dependentBeanMap.get(beanName);
		if (dependentBeans == null) {
			return new String[0];
		}
		return (String[]) dependentBeans.toArray();
	}

	protected String[] getDependenciesForBean(String beanName) {
		Set<String> dependenciesForBean = this.dependenciesForBeanMap.get(beanName);
		if (dependenciesForBean == null) {
			return new String[0];
		}
		return (String[]) dependenciesForBean.toArray();

	}
}
