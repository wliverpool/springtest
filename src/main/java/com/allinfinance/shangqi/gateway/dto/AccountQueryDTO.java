package com.allinfinance.shangqi.gateway.dto;

public class AccountQueryDTO implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2731316107447387603L;

	private String cardNo;//卡号
	
	private String fullName;//真实姓名
	
	private String cardType;//卡类型
	
	private String balance;//卡备用金余额
	
	private String cardBalance;//卡余额
	
	private String loyaltyBalance;//卡备用金积分
	
	private String cardLoyaltyBalance;//卡积分
	
	private String cardStatus;//卡状态

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getCardBalance() {
		return cardBalance;
	}

	public void setCardBalance(String cardBalance) {
		this.cardBalance = cardBalance;
	}

	public String getLoyaltyBalance() {
		return loyaltyBalance;
	}

	public void setLoyaltyBalance(String loyaltyBalance) {
		this.loyaltyBalance = loyaltyBalance;
	}

	public String getCardLoyaltyBalance() {
		return cardLoyaltyBalance;
	}

	public void setCardLoyaltyBalance(String cardLoyaltyBalance) {
		this.cardLoyaltyBalance = cardLoyaltyBalance;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
	
	
	

}
