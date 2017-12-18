package com.allinfinance.framework.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 用来显示分页信息的DTO
 * 
 * @author jianji.dai
 * 
 */
public class PageDataDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3794058306034224951L;

	/**
	 * 分页数据列表
	 */
	private List<?> data;

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	/**
	 * 总记录数
	 */
	private int totalRecord=0;

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	/**
	 * 每页显示记录数
	 */
	private int pageSize = 50;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
