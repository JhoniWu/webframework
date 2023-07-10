package com.minis.core;

import com.minis.core.Resource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;

/**
 * @program: MicroSpring
 * @description: 解析抽象自resource的beanxml
 * @author: Max Wu
 * @create: 2023-07-10 16:16
 **/
public class ClassPathXmlResource implements Resource {
	Document document;
	Element rootElement;
	Iterator<Element> elementIterator;

	public ClassPathXmlResource(String fileName){
		SAXReader saxReader = new SAXReader();
		URL xmlPath = this.getClass().getClassLoader().getResource(fileName);
		try {
			this.document = saxReader.read(xmlPath);
			this.rootElement = this.document.getRootElement();
			this.elementIterator = this.rootElement.elementIterator();
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean hasNext() {
		return this.elementIterator.hasNext();
	}

	@Override
	public Object next() {
		return this.elementIterator.next();
	}
}
