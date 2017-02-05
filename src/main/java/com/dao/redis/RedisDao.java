package com.dao.redis;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.service.LoadCallBack;

public class RedisDao {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private StringRedisTemplate stringTemplate;

	public StringRedisTemplate getStringTemplate() {
		return stringTemplate;
	}

	public void setStringTemplate(StringRedisTemplate stringTemplate) {
		this.stringTemplate = stringTemplate;
	}
	
	public <T> T getObject(String key,long expire,TypeReference<T> clazz,LoadCallBack<T> load){
		String value = stringTemplate.opsForValue().get(key)+"";
		if(logger.isDebugEnabled()){
			logger.debug("======get from redis key:" + key + ",value:" + value + "==========");
		}
		if(StringUtils.isBlank(value)||value.equalsIgnoreCase("null")){
			synchronized (this) {
				value = stringTemplate.opsForValue().get(key)+"";
				if(StringUtils.isBlank(value)||value.equalsIgnoreCase("null")){
					T t = load.load();
					String jsonString = JSON.toJSONString(t);
					if(logger.isDebugEnabled()){
						logger.debug("======get from db key:" + key + ",value:" + jsonString + "==========");
					}
					stringTemplate.opsForValue().set(key, jsonString,expire,TimeUnit.HOURS);
					return t;
				}
				if(logger.isDebugEnabled()){
					logger.debug("======get from redis key:" + key + ",value:" + value + "==========");
				}
				return JSON.parseObject(value,clazz);
			}
		}else {
			return JSON.parseObject(value, clazz);
		}
	}

}
