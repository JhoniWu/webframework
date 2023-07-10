package com.minis;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: MicroSpring
 * @description:
 * @author: Max Wu
 * @create: 2023-07-10 11:08
 **/
public class ClassPathXmlApplicationContext {
	private List<BeanDefinition> beanDefinitions = new ArrayList<>();
	private Map<String, Object> singletons = new HashMap<>();
	//构造器获取外部配置，解析出Bean，形成内存映像
	public ClassPathXmlApplicationContext (String fileName) {
		this.readXml(fileName);
		this.instanceBeans();
	}
	//读取xml
	private void readXml(String filename){
		SAXReader saxReader = new SAXReader();
		try {
			URL xmlPath = this.getClass().getClassLoader().getResource(filename);
			Document document = saxReader.read(xmlPath);
			Element rootElement = document.getRootElement();
			//对配置文件中的每一个 <bean>， 进行处理
			for(Element element : (List<Element>) rootElement.elements()){
				//获取Bean
				String beanID = element.attributeValue("id");
				String beanClassName = element.attributeValue("class");
				BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
				//存放进beanDefinitions
				beanDefinitions.add(beanDefinition);
			}
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		}
	}
	//实例化Bean，并存储在singletons
	private void instanceBeans(){
		for(BeanDefinition beanDefinition : beanDefinitions){
			try {
				singletons.put(beanDefinition.getId(), Class.forName(beanDefinition.getClassName()).newInstance());
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 获取Bean实例
	 * @param beanName 		beanName
	 * @return 				bean实例
	 */
	public Object getBean(String beanName){
		return singletons.get(beanName);
	}
}
