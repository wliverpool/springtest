package com.allinfinance.framework.dto;

public class OperationCtx {
	/**
	 * 交易代码
	 */
	protected String txncode;

	/**
	 * 是否使用大数据字段
	 */
	protected String oprtType;

	/**
	 * 操作员ID
	 */
	protected String oprId;
	/**
	 * 操作类型
	 */
	protected String oprType;
	/**
	 * 交易密码
	 */
	protected String txnPwd;
	/**
	 * 是否打印日志
	 */
	protected boolean logAbled = true;
	
	/**
	 * 是否要传输文件
	 */
	protected boolean transfile = false;

	public boolean isTransfile() {
		return transfile;
	}

	public void setTransfile(boolean transfile) {
		this.transfile = transfile;
	}

	protected String sign;

	protected String plainTxt;

	public String getTxncode() {
		return txncode;
	}

	public void setTxncode(String txncode) {
		this.txncode = txncode;
	}

	public String getOprtType() {
		return oprtType;
	}

	public void setOprtType(String oprtType) {
		this.oprtType = oprtType;
	}

	public String getOprId() {
		return oprId;
	}

	public void setOprId(String oprId) {
		this.oprId = oprId;
	}

	public String getOprType() {
		return oprType;
	}

	public void setOprType(String oprType) {
		this.oprType = oprType;
	}

	public String getTxnPwd() {
		return txnPwd;
	}

	public void setTxnPwd(String txnPwd) {
		this.txnPwd = txnPwd;
	}

	public boolean isLogAbled() {
		return logAbled;
	}

	public void setLogAbled(boolean logAbled) {
		this.logAbled = logAbled;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getPlainTxt() {
		return plainTxt;
	}

	public void setPlainTxt(String plainTxt) {
		this.plainTxt = plainTxt;
	}
}
