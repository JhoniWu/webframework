package com.minis.core;

/**
 * @program: MicroSpring
 * @description:
 * @author: Max Wu
 * @create: 2023-07-11 11:05
 **/
public class PropertyValue {
	private final String name;
	private final Object value;


	public PropertyValue(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}
}
