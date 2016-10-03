package com.dao.util;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 自定义动态数据源类,继承了spring的AbstractRoutingDataSource数据源路由抽象类
 * 通过determineCurrentLookupKey方法获取使用的数据源
 * @author 吴福明
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicDataSourceHolder.getDbType();
	}

}