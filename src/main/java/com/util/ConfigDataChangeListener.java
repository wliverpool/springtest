package com.util;

/**
 * 监听器，监听配置的节点内容改变
 * 
 * @author mittermeyer
 * 
 */
public abstract interface ConfigDataChangeListener {
	public abstract void configChanged(String key, String newValue);
}