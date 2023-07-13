package com.minis.beans;

import com.minis.core.PropertyValue;
import com.minis.core.Resource;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;


/**
 * @program: MicroSpring
 * @description: 读取xml文件获取bean实例
 * @author: Max Wun
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
			//处理属性和构造器参数
			getPropertyElements(element, beanDefinition);
			getConstructElements(element, beanDefinition);
			//注册bean定义
			this.beanFactory.registerBeanDefinition(beanID, beanDefinition);
		}
	}

	private static void getPropertyElements(Element element, BeanDefinition beanDefinition) {
		//处理属性
		List<Element> propertyElements = element.elements("type");
		PropertyValues PVS = new PropertyValues();
		List<String> refs = new ArrayList<>();
		for(Element e : propertyElements){
			String pType = e.attributeValue("type");
			String pName = e.attributeValue("name");
			String pValue = e.attributeValue("value");
			String pRef = e.attributeValue("ref");
			String pV = "";
			boolean isRef = false;
			if(pValue != null && !pValue.equals("")){
				isRef = false;
				pV = pValue;
			} else if(pRef != null && !pRef.equals("")){
				isRef = true;
				pV = pRef;
				refs.add(pRef);
			}
			PVS.addPropertyValue(new PropertyValue(pName, pValue, pType, isRef));
		}
		beanDefinition.setPropertyValues(PVS);
		String[] refArray = refs.toArray(new String[0]);
		beanDefinition.setDependsOn(refArray);
	}

	private static void getConstructElements(Element element, BeanDefinition beanDefinition) {
		//处理构造器函数
		List<Element> constructElements = element.elements("constructor-arg");
		ArgumentValues AVS = new ArgumentValues();
		for(Element e : constructElements){
			String aType = e.attributeValue("type");
			String aName = e.attributeValue("name");
			String aValue = e.attributeValue("value");
			AVS.addArgumentValue(new ArgumentValue(aType, aName, aValue));
		}
		beanDefinition.setConstructArgumentValues(AVS);
	}
}

