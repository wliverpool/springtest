package com.dao.redis;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.pojo.SecKillResult;
import com.service.LoadCallBack;

public class RedisDao {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private StringRedisTemplate stringTemplate;
	private RedisScript<String> updateAvailableSavingsCard;
	private RedisTemplate<String, String> template;
	
	public RedisTemplate<String, String> getTemplate() {
		return template;
	}

	public void setTemplate(RedisTemplate<String, String> template) {
		this.template = template;
	}

	public RedisScript<String> getUpdateAvailableSavingsCard() {
		return updateAvailableSavingsCard;
	}

	public void setUpdateAvailableSavingsCard(RedisScript<String> updateAvailableSavingsCard) {
		this.updateAvailableSavingsCard = updateAvailableSavingsCard;
	}

	public StringRedisTemplate getStringTemplate() {
		return stringTemplate;
	}

	public void setStringTemplate(StringRedisTemplate stringTemplate) {
		this.stringTemplate = stringTemplate;
	}
	
	public String executeScript(String numKey,String userPhone,String infoKey){
		String SecKillHsetName = "miaosha_" + numKey + "_user";
		return stringTemplate.execute(updateAvailableSavingsCard, Arrays.asList(numKey,SecKillHsetName,infoKey),userPhone,String.valueOf(System.currentTimeMillis()));
	}
	
	public void setObject(String key,long expire,String value){
		if(expire>0){
			stringTemplate.opsForValue().set(key, value,expire ,TimeUnit.HOURS);
		}else{
			stringTemplate.opsForValue().set(key, value);
		}
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
					setObject(key,expire,jsonString);
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
