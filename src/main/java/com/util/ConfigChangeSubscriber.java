package com.util;

import java.util.List;

/**
 * 配置改变的订阅者，在每一個zk文件上订阅一個监听器
 * 
 * @author mittermeyer
 *
 */
public abstract interface ConfigChangeSubscriber {
	public abstract String getInitValue(String paramString);

	/**
	 * 订阅zookeeper中节点内容变化,
	 * @param key
	 * @param paramConfigChangeListener
	 */
	public abstract void subscribeDataChanges(String key,ConfigDataChangeListener paramConfigChangeListener);
	
	/**
	 * 订阅zookeeper中子节点个数变化
	 * @param key
	 * @param listener
	 */
	public abstract void subscribeChildrenChanges(ConfigChildrenChangeListener listener);
	

	public abstract List<String> listKeys();
}