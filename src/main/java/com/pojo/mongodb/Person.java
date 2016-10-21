package com.pojo.mongodb;

import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = 6554398884593785721L;

	private String id;
	private int no;
	private String name;
	private String loc;

	public Person(String id, int no, String name, String loc) {
		super();
		this.id = id;
		this.no = no;
		this.name = name;
		this.loc = loc;
	}

	public Person() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String toString() {
		return "[" + "name:" + this.name + ",no:" + no + ",loc:" + loc + "]";
	}

}
