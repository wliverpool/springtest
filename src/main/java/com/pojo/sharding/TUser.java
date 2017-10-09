package com.pojo.sharding;

import java.io.Serializable;

public class TUser implements Serializable{

	private static final long serialVersionUID = 7306458425878449112L;
	
	private int userId;
	private String name;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
