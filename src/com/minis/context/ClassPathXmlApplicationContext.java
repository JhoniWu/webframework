package com.minis.context;

import com.minis.beans.*;
import com.minis.core.ClassPathXmlResource;
import com.minis.core.Resource;

/**
 * @program: MicroSpring
 * @description:
 * @author: Max Wu
 * @create: 2023-07-10 11:08
 **/
public class ClassPathXmlApplicationContext implements BeanFactory {
	BeanFactory beanFactory;
	//context负责整合容器的启动过程，读取外部配置，解析Bean定义，创建BeanFactory
	public ClassPathXmlApplicationContext(String fileName){
		//解析xml内容，放入resource中，resource即是原来的bean.xml抽象资源
		Resource resource = new ClassPathXmlResource(fileName);
		//实例化一个BeanFactory
		BeanFactory beanFactoryIn = new SimpleBeanFactory();
		//创建一个读取xml的reader
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactoryIn);
		//reader进行读取操作，同时将BeanFactory更新，加入bean配置
		reader.loadBeanDefinitions(resource);
		this.beanFactory = beanFactoryIn;
	}
	@Override
	public Object getBean(String beanName) throws BeansException {
		return  this.beanFactory.getBean(beanName);
	}

	@Override
	public void registerBeanDefinition(BeanDefinition beanDefinition) {
		this.beanFactory.registerBeanDefinition(beanDefinition);
	}
}
