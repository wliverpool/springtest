package com.pojo.sharding;

import java.io.Serializable;

public class TOrder implements Serializable{
	
	private static final long serialVersionUID = -7016522543006652148L;
	
	private int orderId;
	private int userId;
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
