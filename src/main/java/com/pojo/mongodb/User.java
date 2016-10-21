package com.pojo.mongodb;

import org.bson.Document;

public class User {
	private String name = null;

	private int age = -1;

	private int updateTimes = -1;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getUpdateTimes() {
		return updateTimes;
	}

	public void setUpdateTimes(int updateTimes) {
		this.updateTimes = updateTimes;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + ", updateTimes=" + updateTimes + "]";
	}

	public Document toBasicDBObject() {
		Document user = new Document();
		user.put("name", this.name);
		user.put("age", this.age);
		user.put("updateTimes", this.updateTimes);
		return user;
	}
}