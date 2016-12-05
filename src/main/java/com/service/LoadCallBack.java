package com.service;

/**
 * 提供给缓存服务类使用的回调类,用于处理当在缓存中找不到对应的值时应如何处理
 * @author 吴福明
 *
 * @param <T>
 */
public interface LoadCallBack<T> {
	
	/**
	 * 当在缓存中找不到相应对象时,应该如何加载数据
	 * @return   加载到的相应数据
	 */
	T load();

}
