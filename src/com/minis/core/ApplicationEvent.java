package com.minis.core;

import java.util.EventObject;

/**
 * @program: MicroSpring
 * @description:
 * @author: Max Wu
 * @create: 2023-07-11 10:22
 **/
public class ApplicationEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a prototypical Event.
	 *
	 * @param source the object on which the Event initially occurred
	 * @throws IllegalArgumentException if source is null
	 */
	public ApplicationEvent(Object source) {
		super(source);
	}
}
