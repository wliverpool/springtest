package com.allinfinance.shangqi.gateway.dto;

public class RecordConsumeDTO implements java.io.Serializable{
	
	
	
	/**
	 */
	private static final long serialVersionUID = -1709346693849224878L;

	private String txnCode;//交易码
	
	private String oprId;//用户ID
	
	private String mac;//mac校验数据
	
	private String cardNo;//卡号
	
	private String starDate;//查询起始日期
	
	private String enDate;//查询终止日期
	
	private String startNum;//起始条目编号
	
	private String endNum;//结束条目编号
	
	private String sumNum;//总条数
	
	private  String[] data;//返回的数据

	public String getSumNum() {
		return sumNum;
	}

	public void setSumNum(String sumNum) {
		this.sumNum = sumNum;
	}

	public String[] getData() {
		return data;
	}

	public void setData(String[] data) {
		this.data = data;
	}

	public String getTxnCode() {
		return txnCode;
	}

	public void setTxnCode(String txnCode) {
		this.txnCode = txnCode;
	}

	public String getOprId() {
		return oprId;
	}

	public void setOprId(String oprId) {
		this.oprId = oprId;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getStarDate() {
		return starDate;
	}

	public void setStarDate(String starDate) {
		this.starDate = starDate;
	}

	public String getEnDate() {
		return enDate;
	}

	public void setEnDate(String enDate) {
		this.enDate = enDate;
	}

	public String getStartNum() {
		return startNum;
	}

	public void setStartNum(String startNum) {
		this.startNum = startNum;
	}

	public String getEndNum() {
		return endNum;
	}

	public void setEndNum(String endNum) {
		this.endNum = endNum;
	}
	
}
