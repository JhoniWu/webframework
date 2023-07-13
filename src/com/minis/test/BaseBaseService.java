package com.minis.test;

/**
 * @program: MicroSpring
 * @description:
 * @author: Max Wu
 * @create: 2023-07-13 10:16
 **/
public class BaseBaseService {
	private AServiceImpl as;

	public AServiceImpl getAs() {
		return as;
	}
	public void setAs(AServiceImpl as) {
		this.as = as;
	}
	public BaseBaseService() {
	}
	public void sayHello() {
		System.out.println("Base Base Service says hello");

	}
}
