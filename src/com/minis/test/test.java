package com.minis.test;

import com.minis.beans.BeansException;
import com.minis.context.ClassPathXmlApplicationContext;

/**
 * @program: MicroSpring
 * @description:
 * @author: Max Wu
 * @create: 2023-07-11 10:57
 **/
public class test {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		try {
			AService aService = (AService) ctx.getBean("aservice");
		} catch (BeansException e) {
			throw new RuntimeException(e);
		}

	}
}
