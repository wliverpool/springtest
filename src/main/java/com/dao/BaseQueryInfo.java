package com.dao;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 
 * 查询pojo的基础类 <br>
 * @author 吴福明
 */
public class BaseQueryInfo implements Serializable {

	private static final long serialVersionUID = -2282808190890211326L;
	/**
	 * 排序字段名
	 */
	protected String sidx;// sortKey
	/**
	 * 排序方式
	 */
	protected String sord;// sortType

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	/**
	 * toString 方法通过反射得到子类的属性与属性值 如果不需要所有的属性值就在子类里从写toString
	 */
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SIMPLE_STYLE);
	}
	
}

