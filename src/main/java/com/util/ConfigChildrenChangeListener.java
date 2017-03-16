package com.util;

import java.util.List;

/**
 * 监听器，监听子节点个数改变
 * 
 * @author mittermeyer
 * 
 */
public abstract interface ConfigChildrenChangeListener {
	public abstract void configChanged(String parentPath, List<String> currentChilds);
}
