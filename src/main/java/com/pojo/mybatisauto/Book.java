package com.pojo.mybatisauto;

import java.util.List;

public class Book {

	private int bid;
	private String bname;
	private String bprice;
	private List<Browser> browsers;

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public String getBprice() {
		return bprice;
	}

	public void setBprice(String bprice) {
		this.bprice = bprice;
	}

	public List<Browser> getBrowsers() {
		return browsers;
	}

	public void setBrowsers(List<Browser> browsers) {
		this.browsers = browsers;
	}

}
