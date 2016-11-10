package com.pojo.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Student {
	@Id
	private String id;
	
	private String stuName;
	@DBRef
	private ClassInfo classObj;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getStuName() {
		return stuName;
	}
	
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	
	public ClassInfo getClassObj() {
		return classObj;
	}
	
	public void setClassObj(ClassInfo classObj) {
		this.classObj = classObj;
	}

}
