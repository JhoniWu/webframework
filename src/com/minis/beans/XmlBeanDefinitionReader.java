package com.minis.beans;

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
	SimpleBeanFactory bf;

	public XmlBeanDefinitionReader(SimpleBeanFactory beanFactory) {
		this.bf = beanFactory;
	}

	public void loadBeanDefinitions(Resource res) {
		while (res.hasNext()) {
			Element element = (Element) res.next();
			String beanID = element.attributeValue("id");
			String beanClassName = element.attributeValue("class");

			BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);

			//get constructor
			List<Element> constructorElements = element.elements("constructor-arg");
			ArgumentValues AVS = new ArgumentValues();
			for (Element e : constructorElements) {
				String pType = e.attributeValue("type");
				String pName = e.attributeValue("name");
				String pValue = e.attributeValue("value");
				AVS.addArgumentValue(new ArgumentValue(pType, pName, pValue));
			}
			beanDefinition.setConstructorArgumentValues(AVS);
			//end of handle constructor

			//handle properties
			List<Element> propertyElements = element.elements("property");
			PropertyValues PVS = new PropertyValues();
			List<String> refs = new ArrayList<>();
			for (Element e : propertyElements) {
				String pType = e.attributeValue("type");
				String pName = e.attributeValue("name");
				String pValue = e.attributeValue("value");
				String pRef = e.attributeValue("ref");
				String pV = "";
				boolean isRef = false;
				if (pValue != null && !pValue.equals("")) {
					isRef = false;
					pV = pValue;
				} else if (pRef != null && !pRef.equals("")) {
					isRef = true;
					pV = pRef;
					refs.add(pRef);
				}
				PVS.addPropertyValue(new PropertyValue(pType, pName, pV, isRef));
			}
			beanDefinition.setPropertyValues(PVS);
			String[] refArray = refs.toArray(new String[0]);
			beanDefinition.setDependsOn(refArray);
			//end of handle properties

			this.bf.registerBeanDefinition(beanID, beanDefinition);
		}
	}

}

