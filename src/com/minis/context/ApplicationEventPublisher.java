package com.minis.context;

/**
 * @program: MicroSpring
 * @description:
 * @author: Max Wu
 * @create: 2023-07-11 10:20
 **/
public interface ApplicationEventPublisher {
	void publishEvent(ApplicationEvent event);
}
