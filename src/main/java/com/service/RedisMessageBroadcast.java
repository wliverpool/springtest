package com.service;

import org.springframework.data.redis.core.StringRedisTemplate;

import com.pojo.User;

import net.sf.json.JSONObject;

public class RedisMessageBroadcast {

	private StringRedisTemplate template;

	public StringRedisTemplate getTemplate() {
		return template;
	}

	public void setTemplate(StringRedisTemplate template) {
		this.template = template;
	}

	public boolean sendRedisBroadcastMessage(User user) {
		template.convertAndSend("javatst", JSONObject.fromObject(user).toString());
		return true;
	}

}
