package com.allinfinance.shangqi.gateway.dto;

import com.allinfinance.framework.dto.BaseDTO;

public class AccountConsumDTO extends BaseDTO{
	
	private String cardNo;//卡号
	
	private String phone;//手机号
	
	private String money;//交易金额
	
	private String sysTraceAuditNum;//流水号(posp  6位)
	
	private String traceAuditNum;//流水号(18位)
	
	//private String CardAccptrTermnlId;//终端号
	
	//private String CardAccptrId;//商户号
	
	private String sPinData;//个人标识码数据
	
//	private String entityId;//机构号
	
	private String chanlTime;//门户的时间
	
	
//	private String TxnTlr;//操作员号
	
	
	
	
	
	
	
	
	
	
//
//	public String getTxnTlr() {
//		return TxnTlr;
//	}
//
//	public void setTxnTlr(String txnTlr) {
//		TxnTlr = txnTlr;
//	}

	public String getChanlTime() {
		return chanlTime;
	}

	public String getTraceAuditNum() {
		return traceAuditNum;
	}

	public void setTraceAuditNum(String traceAuditNum) {
		this.traceAuditNum = traceAuditNum;
	}

	public void setChanlTime(String chanlTime) {
		this.chanlTime = chanlTime;
	}

//	public String getEntityId() {
//		return entityId;
//	}
//
//	public void setEntityId(String entityId) {
//		this.entityId = entityId;
//	}

	public String getSPinData() {
		return sPinData;
	}

	public void setSPinData(String sPinData) {
		this.sPinData = sPinData;
	}

	public String getSysTraceAuditNum() {
		return sysTraceAuditNum;
	}

	public void setSysTraceAuditNum(String sysTraceAuditNum) {
		this.sysTraceAuditNum = sysTraceAuditNum;
	}

//	public String getCardAccptrTermnlId() {
//		return CardAccptrTermnlId;
//	}
//
//	public void setCardAccptrTermnlId(String cardAccptrTermnlId) {
//		CardAccptrTermnlId = cardAccptrTermnlId;
//	}
//
//	public String getCardAccptrId() {
//		return CardAccptrId;
//	}
//
//	public void setCardAccptrId(String cardAccptrId) {
//		CardAccptrId = cardAccptrId;
//	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
	
	

}
