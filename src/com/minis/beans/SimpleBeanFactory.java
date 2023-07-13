package com.minis.beans;

import com.minis.core.PropertyValue;
import java.lang.reflect.Method;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: MicroSpring
 * @description:
 * @author: Max Wu
 * @create: 2023-07-10 16:31
 **/
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory{
	private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
	private List<String> beanDefinitionNames = new ArrayList<>();
	//毛胚实例
	private final Map<String, Object> earlySingletonObjects = new HashMap<String, Object>(16);

	public SimpleBeanFactory(){
	}

	public void refresh(){
		for(String beanName : beanDefinitionNames){
			try {
				getBean(beanName);
			} catch (BeansException e) {
				e.printStackTrace();
			}
		}
	}

	//获取Bean，核心方法
	@Override
	public Object getBean(String beanName) throws BeansException {
		//尝试从容器中直接获取Bean实例
		Object singleton = this.getSingleton(beanName);
		if(singleton == null) {
			//如果没有，从毛胚实例中获取
			singleton = this.earlySingletonObjects.get(beanName);
			//BeanDefinition definition = beanDefinitionMap.get(beanName);
			if(singleton == null) {
				//如果没有毛胚实例，则创建bean实例并且注册
				System.out.println("get bean null---------------");
				BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
				singleton = createBean(beanDefinition);
				this.registerBean(beanName, singletons);
				//预留beanpostprocessor位置
				//
				//
				//
			}

        }
        if (singleton == null) {
        	throw new BeansException("bean is null.");
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
				e.printStackTrace();
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


	private Object createBean(BeanDefinition beanDefinition){
		Class<?> clz = null;
		//创建bean毛胚
		Object obj = doCreateBean(beanDefinition);
		this.earlySingletonObjects.put(beanDefinition.getId(), obj);

		try {
			clz = Class.forName(beanDefinition.getClassName());
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}

		handleProperties(beanDefinition, clz, obj);
		return obj;
	}

	private Object doCreateBean(BeanDefinition beanDefinition) {
		Class<?> clz = null;
		Object obj = null;
		Constructor<?> con = null;

		try{
			clz = Class.forName(beanDefinition.getClassName());
			//处理构造器参数
			ArgumentValues argumentValues = beanDefinition.getConstructArgumentValues();
			if(!argumentValues.isEmpty()){
				Class<?>[] paramTypes = new Class<?>[argumentValues.getArgumentCount()];
				Object[] paramValues = new Object[argumentValues.getArgumentCount()];
				//对于每一个参数，分数据类型分别处理
				for(int i = 0; i< argumentValues.getArgumentCount(); i++){
					ArgumentValue argumentValue = argumentValues.getIndexedArgument(i);
					if("String".equals(argumentValue.getType()) || "java.lang.String".equals(argumentValue.getType())) {
						paramTypes[i] = String.class;
						paramValues[i] = argumentValue.getValue();
					} else if("Integer".equals(argumentValue.getType()) || "java.lang.Integer".equals(argumentValue.getType())) {
						paramTypes[i] = Integer.class;
						paramValues[i] = Integer.valueOf((String)argumentValue.getValue());
					} else if("int".equals(argumentValue.getType())) {
						paramTypes[i] = int.class;
						paramValues[i] = Integer.valueOf((String)argumentValue.getValue()).intValue();
					} else {
						paramTypes[i] = String.class;
						paramValues[i] = argumentValue.getValue();
					}
				}

				try {
					con = clz.getConstructor(paramTypes);
					obj = con.newInstance(paramValues);
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			} else {
				//如果无参，直接创建实例
				obj = clz.newInstance();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(obj!=null)
			System.out.println(beanDefinition.getId() + " bean created. " + beanDefinition.getClassName() + " : " + obj.toString());
		else System.out.println(beanDefinition.getId() + " bean created. " + beanDefinition.getClassName() + " : ");
		return obj;
	}

	private static void handlePropertyValues(BeanDefinition beanDefinition, Class<?> clz, Object obj) {
		PropertyValues propertyValues = beanDefinition.getPropertyValues();
		if(!propertyValues.isEmpty()){
			for(int i = 0; i < propertyValues.size(); i++){
				//对每一个属性，分数据类型分别处理
				PropertyValue propertyValue = propertyValues.getPropertyValues().get(i);
				String pType = propertyValue.getType();
				String pName = propertyValue.getName();
				Object pValue = propertyValue.getValue();

				Class<?>[] paramTypes = new Class<?>[1];
				if("String".equals(pType) || "java.lang.String".equals(pType)) {
					paramTypes[0] = String.class;
				} else if("Integer".equals(pType) || "java.lang.Integer".equals(pType)) {
					paramTypes[0] = Integer.class;
				} else if("int".equals(pType)) {
					paramTypes[0] = int.class;
				} else {
					paramTypes[0] = String.class;
				}

				Object[] paramValues = new Object[1];

				paramValues[0] = pValue;
				//按照setXxxx规范查找setter方法，调用setter方法设置属性

				String methodName = "set" + pName.substring(0,1).toUpperCase() + pName.substring(1);
				Method method = null;
				try {
					method = clz.getMethod(methodName, paramTypes);
				} catch (Exception e) {

				}
				try {
					method.invoke(obj, paramValues);
				} catch (Exception e){
				}
			}
		}
	}

	private void handleProperties(BeanDefinition bd, Class<?> clz, Object obj) {
		//处理属性
		System.out.println("handle properties for bean : " + bd.getId());
		PropertyValues propertyValues = bd.getPropertyValues();
		//如果有属性
		if(!propertyValues.isEmpty()){
			for(int i = 0; i < propertyValues.size() ; i++){
				PropertyValue propertyValue = propertyValues.getPropertyValues().get(i);
				String pName = propertyValue.getName();
				String pType = propertyValue.getType();
				Object pValue = propertyValue.getValue();
				boolean isRef = propertyValue.getIsRef();
    			Class<?>[] paramTypes = new Class<?>[1];
				Object[] paramValues = new Object[1];
				if(!isRef){
					if ("String".equals(pType) || "java.lang.String".equals(pType)) {
						paramTypes[0] = String.class;
					}else if("Integer".equals(pType) || "java.lang.Integer".equals(pType)){
						paramTypes[0] = Integer.class;
					}else if("int".equals(pType)){
						paramTypes[0] = int.class;
					}else {
						paramTypes[0] = String.class;
					}
					paramValues[0] = pValue;
				} else {
					try {
						paramTypes[0] = Class.forName(pType);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}

					try {
						paramValues[0] = getBean((String)pValue);
					} catch (BeansException e) {
						e.printStackTrace();
					}
				}

    			String methodName = "set" + pName.substring(0,1).toUpperCase() + pName.substring(1);

				Method method = null;

				try {
					method = clz.getMethod(methodName, paramTypes);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}

				try {
					method.invoke(obj, paramValues);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}


}
