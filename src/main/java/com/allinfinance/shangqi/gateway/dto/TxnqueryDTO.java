package com.allinfinance.shangqi.gateway.dto;

import com.allinfinance.framework.dto.PageQueryDTO;

public class TxnqueryDTO extends PageQueryDTO{
		private String cardNo; //卡号
		private String starDate; //起始日期
		private String enDate; //终止日期
		private String txnStat; //交易状态
		private String mac;    //mac校验值
		private String orderNo; //订单号
		private String payType; //支付类型
		private String banlance; //交易金额
		private String txnDate; //交易日期
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
		public String getTxnStat() {
			return txnStat;
		}
		public void setTxnStat(String txnStat) {
			this.txnStat = txnStat;
		}
		public String getMac() {
			return mac;
		}
		public void setMac(String mac) {
			this.mac = mac;
		}
		public String getOrderNo() {
			return orderNo;
		}
		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}
		public String getPayType() {
			return payType;
		}
		public void setPayType(String payType) {
			this.payType = payType;
		}
		public String getBanlance() {
			return banlance;
		}
		public void setBanlance(String banlance) {
			this.banlance = banlance;
		}
		public String getTxnDate() {
			return txnDate;
		}
		public void setTxnDate(String txnDate) {
			this.txnDate = txnDate;
		}
		
}
