package com.util;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang.StringUtils;

/**
 * 订阅者实现类，当订阅到zk数据改变时，会触发ConfigChangeListener
 * 
 * @author mittermeyer
 * 
 */
public class ZkConfigChangeSubscriberImpl implements ConfigChangeSubscriber {
	private ZkClient zkClient;
	private String rootNode;

	public ZkConfigChangeSubscriberImpl(ZkClient zkClient, String rootNode) {
		this.rootNode = rootNode;
		this.zkClient = zkClient;
	}

	public void subscribeDataChanges(String key, ConfigDataChangeListener listener) {
		String path = ZkUtils.getZkPath(this.rootNode, key);
		if (!this.zkClient.exists(path)) {
			throw new RuntimeException("配置(" + path + ")不存在, 必须先定义配置才能监听配置的变化, 请检查配置的key是否正确, 如果确认配置key正确, 那么需要保证先使用配置发布命令发布配置! ");
		}
		DataListenerAdapter listenerAdapter = new DataListenerAdapter();
		listenerAdapter.setConfigDataChangeListener(listener);
		this.zkClient.subscribeDataChanges(path, listenerAdapter);
	}
	
	public void subscribeChildrenChanges(ConfigChildrenChangeListener listener) {
		if (!this.zkClient.exists(rootNode)) {
			throw new RuntimeException("配置(" + rootNode + ")不存在, 必须先定义配置才能监听配置的变化, 请检查配置的key是否正确, 如果确认配置key正确, 那么需要保证先使用配置发布命令发布配置! ");
		}
		DataListenerAdapter listenerAdapter = new DataListenerAdapter();
		listenerAdapter.setChildrenChangeListener(listener);
		this.zkClient.subscribeChildChanges(rootNode, listenerAdapter);
	}

	/**
	 * 触发节点内容ConfigChangeListener
	 * 
	 * @param path
	 * @param value
	 * @param configListener
	 */
	private void fireConfigDataChanged(String path, String newValue,ConfigDataChangeListener configListener) {
		configListener.configChanged(getKey(path), newValue);
	}
	
	/**
	 * 触发子节点个数变化
	 * @param path
	 * @param currentChilds
	 * @param configListener
	 */
	private void fireConfigChildrenChanged(String path, List<String> currentChilds,ConfigChildrenChangeListener configListener){
		configListener.configChanged(path, currentChilds);
	}

	private String getKey(String path) {
		String key = path;

		if (!StringUtils.isEmpty(this.rootNode)) {
			key = path.replaceFirst(this.rootNode, "");
			if (key.startsWith("/")) {
				key = key.substring(1);
			}
		}

		return key;
	}

	public String getInitValue(String key) {
		String path = ZkUtils.getZkPath(this.rootNode, key);
		return (String) this.zkClient.readData(path);
	}

	public List<String> listKeys() {
		return this.zkClient.getChildren(this.rootNode);
	}

	/**
	 * 数据监听器适配类，订阅节点数据变化,当zk数据变化时，触发ConfigChangeListener
	 * 
	 * @author june
	 * 
	 */
	private class DataListenerAdapter implements IZkDataListener,IZkChildListener {
		//节点内容变化监听
		private ConfigDataChangeListener configDataChangeListener;
		//子节点个数变化监听
		private ConfigChildrenChangeListener childrenChangeListener;

		public void setConfigDataChangeListener(ConfigDataChangeListener configDataChangeListener) {
			this.configDataChangeListener = configDataChangeListener;
		}

		public void setChildrenChangeListener(ConfigChildrenChangeListener childrenChangeListener) {
			this.childrenChangeListener = childrenChangeListener;
		}

		/**
		 * 处理节点内容变化
		 */
		public void handleDataChange(String s, Object obj) throws Exception {
			ZkConfigChangeSubscriberImpl.this.fireConfigDataChanged(s,(String) obj, this.configDataChangeListener);
		}

		public void handleDataDeleted(String s) throws Exception {
			ZkConfigChangeSubscriberImpl.this.fireConfigDataChanged(s, "null", this.configDataChangeListener);
		}
		
		/**
		 * 处理节点中创建子节点，删除子节点，添加子节点
		 */
		public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {  
			ZkConfigChangeSubscriberImpl.this.fireConfigChildrenChanged(parentPath,currentChilds,childrenChangeListener);
        }  
	}
}