package com.service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisMessageListener {

	private StringRedisTemplate template;

	public StringRedisTemplate getTemplate() {
		return template;
	}

	public void setTemplate(StringRedisTemplate template) {
		this.template = template;
	}

	public void handleMessage(Serializable message) {
		// 什么都不做,只输出
		if (message == null) {
			System.out.println("null");
		} else if (message.getClass().isArray()) {
			System.out.println(Arrays.toString((Object[]) message));
		} else if (message instanceof List<?>) {
			System.out.println(message);
		} else if (message instanceof Map<?, ?>) {
			System.out.println(message);
		} else {
			System.out.println(ToStringBuilder.reflectionToString(message));
		}
	}

}
