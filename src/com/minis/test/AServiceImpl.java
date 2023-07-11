package com.minis.test;

/**
 * @program: MicroSpring
 * @description:
 * @author: Max Wu
 * @create: 2023-07-10 15:48
 **/
public class AServiceImpl implements AService{
	private String name;
	private int level;
	public AServiceImpl(String name, int level){
		this.name = name;
		this.level = level;
	}
	@Override
	public void setProperty1(String name, int level) {
		this.name = name;
		this.level = level;
	}
}
