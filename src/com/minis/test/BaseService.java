package com.minis.test;

/**
 * @program: MicroSpring
 * @description:
 * @author: Max Wu
 * @create: 2023-07-13 10:17
 **/
public class BaseService {
	private BaseBaseService bbs;
	public BaseService() {
	}
	public BaseBaseService getBbs() {
		return bbs;
	}

	public void setBbs(BaseBaseService bbs) {
		this.bbs = bbs;
	}
	public void sayHello() {
		System.out.print("Base Service says hello");
		bbs.sayHello();
	}
}
