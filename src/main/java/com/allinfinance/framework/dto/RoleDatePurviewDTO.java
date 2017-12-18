package com.allinfinance.framework.dto;

import java.io.Serializable;
import java.util.Date;

public class RoleDatePurviewDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String roleId;

	private Integer modifyRate;

	private Integer orderPriority;

	private Integer paymentTerm;

	private Integer discountFee;

	private Integer modifyServiceFee;

	private Integer modifyServiceFee2;

	private String createUser;

	private Date createTime;

	private String modifyUser;

	private Date modifyTime;

	private Short dataState;

	private Integer sensitiveData;

	public Integer getSensitiveData() {
		return sensitiveData;
	}

	public void setSensitiveData(Integer sensitiveData) {
		this.sensitiveData = sensitiveData;
	}

	public Integer getModifyRate() {
		return modifyRate;
	}

	public void setModifyRate(Integer modifyRate) {
		this.modifyRate = modifyRate;
	}

	public Integer getOrderPriority() {
		return orderPriority;
	}

	public void setOrderPriority(Integer orderPriority) {
		this.orderPriority = orderPriority;
	}

	public Integer getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(Integer paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public Integer getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(Integer discountFee) {
		this.discountFee = discountFee;
	}

	public Integer getModifyServiceFee() {
		return modifyServiceFee;
	}

	public void setModifyServiceFee(Integer modifyServiceFee) {
		this.modifyServiceFee = modifyServiceFee;
	}

	public Integer getModifyServiceFee2() {
		return modifyServiceFee2;
	}

	public void setModifyServiceFee2(Integer modifyServiceFee2) {
		this.modifyServiceFee2 = modifyServiceFee2;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Short getDataState() {
		return dataState;
	}

	public void setDataState(Short dataState) {
		this.dataState = dataState;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
}
