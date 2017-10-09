package com.pojo.sharding;

import java.io.Serializable;

public class TOrderItem implements Serializable{

	private static final long serialVersionUID = 4767973866458778790L;
	
	private int itemId;
	private int orderId;
	private int userId;
	
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
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
