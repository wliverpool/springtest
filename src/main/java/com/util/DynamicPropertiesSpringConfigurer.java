package com.util;

import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 自定义spring属性加载从zookeeper中加载
 * @author mittermeyer
 *
 */
public class DynamicPropertiesSpringConfigurer extends PropertyPlaceholderConfigurer {
	private static final Logger logger = Logger.getLogger(DynamicPropertiesSpringConfigurer.class);
	
	private DynamicPropertiesHelperFactory helperFactory;
	private String[] propertiesKeys;

	public void setHelperFactory(DynamicPropertiesHelperFactory helperFactory) {
		this.helperFactory = helperFactory;
	}

	public void setPropertiesKeys(String[] propertiesKeys) {
		this.propertiesKeys = propertiesKeys;
	}

	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
		if (this.propertiesKeys != null) {
			for (String propsKey : this.propertiesKeys) {
				DynamicPropertiesHelper helper = this.helperFactory.getHelper(propsKey);
				//解析从zookeeper中获取到的配置信息,映射到props中
				if (helper != null) {
					Enumeration<String> keys = helper.getPropertyKeys();
					while (keys.hasMoreElements()) {
						String key = (String) keys.nextElement();
						props.put(key, helper.getProperty(key));
					}
				} else {
					logger.warn("配置不存在: " + propsKey);
				}
			}
		}
		super.processProperties(beanFactoryToProcess, props);
	}
}