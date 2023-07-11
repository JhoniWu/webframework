package com.minis.beans;

import com.sun.jdi.connect.Connector;

/**
 * @program: MicroSpring
 * @description:
 * @author: Max Wu
 * @create: 2023-07-10 11:05
 **/
public class BeanDefinition {
	String SCOPE_SINGLETON = "singleton";
	String SCOPE_PROTOTYPE = "prototype";
	private boolean lazyInit = false;
	private String[] dependsOn;
	private ArgumentValues constructArgumentValues;
	private PropertyValues propertyValues;
	private String initMethodName;
	private volatile Object beanClass;
	private String id;
	private String className;
	private String scope = SCOPE_SINGLETON;
	public BeanDefinition (String id, String className){
		this.id = id;
		this.className = className;
	}

	public boolean isSingleton() {
		return SCOPE_SINGLETON.equals(scope);
	}

	public boolean isPrototype() {
		return SCOPE_PROTOTYPE.equals(scope);
	}

	public String getSCOPE_SINGLETON() {
		return SCOPE_SINGLETON;
	}

	public void setSCOPE_SINGLETON(String SCOPE_SINGLETON) {
		this.SCOPE_SINGLETON = SCOPE_SINGLETON;
	}

	public String getSCOPE_PROTOTYPE() {
		return SCOPE_PROTOTYPE;
	}

	public void setSCOPE_PROTOTYPE(String SCOPE_PROTOTYPE) {
		this.SCOPE_PROTOTYPE = SCOPE_PROTOTYPE;
	}

	public boolean isLazyInit() {
		return lazyInit;
	}

	public void setLazyInit(boolean lazyInit) {
		this.lazyInit = lazyInit;
	}

	public String[] getDependsOn() {
		return dependsOn;
	}

	public void setDependsOn(String[] dependsOn) {
		this.dependsOn = dependsOn;
	}

	public ArgumentValues getConstructArgumentValues() {
		return constructArgumentValues;
	}

	public void setConstructArgumentValues(ArgumentValues constructArgumentValues) {
		this.constructArgumentValues = constructArgumentValues;
	}

	public PropertyValues getPropertyValues() {
		return propertyValues;
	}

	public void setPropertyValues(PropertyValues propertyValues) {
		this.propertyValues = propertyValues;
	}

	public String getInitMethodName() {
		return initMethodName;
	}

	public void setInitMethodName(String initMethodName) {
		this.initMethodName = initMethodName;
	}

	public Object getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(Object beanClass) {
		this.beanClass = beanClass;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}
