package com.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class QaReader {
	
	private long readId;
	private long uId;
	//指定json转换时的属性名
	@JsonProperty("create_time")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;
	private String name;
	//不会转换成json属性
	@JsonIgnore
	private String title;
	private String bigType;
	
	public long getReadId() {
		return readId;
	}
	
	public void setReadId(long readId) {
		this.readId = readId;
	}
	
	public long getuId() {
		return uId;
	}
	
	public void setuId(long uId) {
		this.uId = uId;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getBigType() {
		return bigType;
	}
	
	public void setBigType(String bigType) {
		this.bigType = bigType;
	}

}
