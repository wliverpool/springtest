package com.pojo;

import java.util.Date;

public class SeckillVo {
	
	//秒杀是否开始
	private boolean isBegin;
	
	private long secKillId;
	//被md5加密的秒杀地址
	private String md5Url;
	
	private Date startTime;
	
	private Date endTime;
	
	private Date now;

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public boolean isBegin() {
		return isBegin;
	}

	public void setBegin(boolean isBegin) {
		this.isBegin = isBegin;
	}

	public long getSecKillId() {
		return secKillId;
	}

	public void setSecKillId(long secKillId) {
		this.secKillId = secKillId;
	}

	public String getMd5Url() {
		return md5Url;
	}

	public void setMd5Url(String md5Url) {
		this.md5Url = md5Url;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
