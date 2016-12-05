package com.service;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.util.MemcachedUtils;

/**
 * 使用抽象设计模式设计的缓存服务类
 * @author 吴福明
 *
 */
public class CacheService {
	
	/**
	 * 在缓存中查找key名对应的数据
	 * @param key   缓存中的键名
	 * @param expire   缓存的过期时间
	 * @param clazz   json对象原本的java类型
	 * @param load    当缓存键的值不存在时该如何加载数据的处理接口
	 * @return   key名对应的数据
	 */
	public <T> T findCache(String key,Date expire,TypeReference<T> clazz,LoadCallBack<T> load){
		//首先从memcache找查询key对应的值是否存在
		String json = MemcachedUtils.get(key)+"";
		if(StringUtils.isBlank(json)||json.equalsIgnoreCase("null")){
			//当没有在memcache缓存中查找到对应值
			synchronized (this) {
				//进行并发加锁,确保只有一个线程在调用load对象加载资源,其他对象都等待其加载完毕
				//进入同步锁之后再在memcache中查找一次,防止第一次通过load对象加载完毕后,其他等待的对象也再次使用load对象去加载,这样缓存等于无效
				json = MemcachedUtils.get(key)+"";
				if(StringUtils.isBlank(json)||json.equalsIgnoreCase("null")){
					//当第二次从memcache中也未能加载到数据时,使用load对象,使用其他方式加载数据
					T t = load.load();
					//加载完对象之后,把对象放到memcache缓存中
					MemcachedUtils.set(key, JSON.toJSONString(t),expire);
					//最后返回对象
					return t;
				}
				//如果在同步块中能从memcache加载到数据,则直接转换成相应的java对象之后返回
				return JSON.parseObject(json,clazz);
			}
		}else {
			//memcache中找到对应的值
			//把json格式数据转换成相应的java类型并且返回
			return JSON.parseObject(json, clazz);
		}
	}

}
