package com.util;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态配置文件辅助类的工厂类，在创建动态配置文件辅助类时，会订阅zk数据改变的事件
 * 比原先zookeeper推送节点内容变化多推送了对应属性的原始值
 * @author mittermeyer
 *
 */
public class DynamicPropertiesHelperFactory {
	private ConfigChangeSubscriber configChangeSubscriber;
	private ConcurrentHashMap<String, DynamicPropertiesHelper> helpers = new ConcurrentHashMap<String, DynamicPropertiesHelper>();

	public DynamicPropertiesHelperFactory(ConfigChangeSubscriber configChangeSubscriber) {
		this.configChangeSubscriber = configChangeSubscriber;
	}

	/**
	 * 根据key名获取helper中存储的值
	 * @param key
	 * @return
	 */
	public DynamicPropertiesHelper getHelper(String key) {
		DynamicPropertiesHelper helper = (DynamicPropertiesHelper) this.helpers.get(key);
		if (helper != null) {
			return helper;
		}
		//如果helper为空构建helper对象的内容并且获取key名对应的值
		return createHelper(key);
	}

	/**
	 * 从zookeeper中获取key节点的内容，并初始化其他的节点内容的值到helpers中
	 * @param key zk中的一个节点
	 * @return
	 */
	private DynamicPropertiesHelper createHelper(String key) {
		List<String> keys = this.configChangeSubscriber.listKeys();
		if ((keys == null) || (keys.size() == 0)) {
			return null;
		}

		if (!keys.contains(key)) {
			return null;
		}

		String initValue = this.configChangeSubscriber.getInitValue(key);
		final DynamicPropertiesHelper helper = new DynamicPropertiesHelper(initValue);
		DynamicPropertiesHelper old = (DynamicPropertiesHelper) this.helpers.putIfAbsent(key, helper);
		if (old != null) {
			return old;
		}

		/**
		 * 订阅zk数据改变
		 */
		this.configChangeSubscriber.subscribeDataChanges(key, new ConfigDataChangeListener(){
			public void configChanged(String key, String value) {
				helper.refresh(value);
			}
		});
		return helper;
	}
}