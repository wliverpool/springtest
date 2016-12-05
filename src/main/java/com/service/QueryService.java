package com.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;

public class QueryService {
	
	private CacheService cacheService;
	
	public CacheService getCacheService() {
		return cacheService;
	}

	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}

	public Map<String, Object> query(String key,Date expire){
		return cacheService.findCache(key, expire, new TypeReference<Map<String,Object>>(){}, new LoadCallBack<Map<String,Object>>() {
			@Override
			public Map<String,Object> load(){
				//模拟通过其他方式获取map数据
				try {
					System.out.println("===============no cache,load data===========");
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Map<String, Object> result = new HashMap<>();
				result.put("name", key);
				result.put("age", 12);
				return result;
			}
		});
	}

}
