package com.minis.beans;

import com.minis.beans.BeanDefinition;
import com.minis.beans.BeanFactory;
import com.minis.core.Resource;
import org.dom4j.Element;


/**
 * @program: MicroSpring
 * @description:
 * @author: Max Wu
 * @create: 2023-07-10 16:25
 **/
public class XmlBeanDefinitionReader {
	BeanFactory beanFactory;
	public XmlBeanDefinitionReader(BeanFactory beanFactory){
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

