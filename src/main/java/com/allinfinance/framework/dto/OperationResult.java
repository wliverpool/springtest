package com.allinfinance.framework.dto;

/**
 * <p>
 * Title: Accor
 * </p>
 * 
 * <p>
 * Description:该类用来记录调用后台交易返回的信息，如果返回状态txnstate的值为1，则表示调用
 * 后台交易正常，detailvo保存后台返回信息，如果返回状态txnstate的值为0，则表示调用 后台交易异常，txncause保存后台返回异常信息
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author YY
 * @version 1.0
 */

public class OperationResult implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2510985647862170656L;

	/**
	 * 错误信息
	 */
	protected String errMessage;

	/**
	 * 错误信息列表,目前是一个List
	 */
	protected Object txncause;
	/**
	 * 返回时间
	 */
	protected String txndate;

	/**
	 * 调用后来返回结果状态 1表示调用正常 0表示调用错误
	 */
	protected String txnstate;
	/**
	 * 返回结果对象
	 */
	protected Object detailvo;
	/**
	 * 流水号
	 */
	protected String flowcode;
	/**
	 * 保留字段
	 */
	protected String reserved;

	public OperationResult() {
	}

	public String getTxnstate() {
		return txnstate;
	}

	public String getTxndate() {
		return txndate;
	}

	public Object getTxncause() {
		return txncause;
	}

	public String getReserved() {
		return reserved;
	}

	public String getFlowcode() {
		return flowcode;
	}

	public Object getDetailvo() {
		return detailvo;
	}

	public void setTxnstate(String txnstate) {
		this.txnstate = txnstate;
	}

	public void setTxndate(String txndate) {
		this.txndate = txndate;
	}

	public void setTxncause(Object txncause) {
		this.txncause = txncause;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	public void setFlowcode(String flowcode) {
		this.flowcode = flowcode;
	}

	public void setDetailvo(Object detailvo) {
		this.detailvo = detailvo;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}

	public String toString() {
		String sep = "\r\n";
		StringBuffer sb = new StringBuffer();
		sb.append(this.getClass().getName()).append(sep);
		sb.append("[").append("txnstate").append(" = ").append(txnstate)
				.append("]").append(sep);
		sb.append("[").append("txndate").append(" = ").append(txndate).append(
				"]").append(sep);
		sb.append("[").append("flowcode").append(" = ").append(flowcode)
				.append("]").append(sep);
		sb.append("[").append("reserved").append(" = ").append(reserved)
				.append("]").append(sep);
		return sb.toString();
	}

}
