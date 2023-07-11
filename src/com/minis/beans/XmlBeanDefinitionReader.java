package com.minis.beans;

import com.minis.core.Resource;
import org.dom4j.Element;


/**
 * @program: MicroSpring
 * @description: 读取xml文件获取bean实例
 * @author: Max Wu
 * @create: 2023-07-10 16:25
 **/
public class XmlBeanDefinitionReader {
	SimpleBeanFactory beanFactory;
	public XmlBeanDefinitionReader(SimpleBeanFactory beanFactory){
		this.beanFactory = beanFactory;
	}
	public void loadBeanDefinitions(Resource resource){
		while (resource.hasNext()){
			Element element = (Element) resource.next();
			String beanID = element.attributeValue("id");
			String beanClassName = element.attributeValue("class");
			BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
			this.beanFactory.registerBeanDefinition(beanDefinition);
		}
	}
}

