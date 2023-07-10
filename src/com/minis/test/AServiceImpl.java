package com.minis.test;

/**
 * @program: MicroSpring
 * @description:
 * @author: Max Wu
 * @create: 2023-07-10 15:48
 **/
public class AServiceImpl implements AService{
	@Override
	public void sayHello() {
		System.out.println("a service 1 say hello");
	}
}
