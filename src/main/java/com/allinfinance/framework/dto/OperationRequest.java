package com.allinfinance.framework.dto;
/**
 * 
 * @author jianji.dai
 *
 */
public class OperationRequest {
	/**
	 * 
	 */
    protected Object txnvo;
    /**
     * 
     */
    protected String txnCode;
    
    public OperationRequest() {
    }

    public Object getTxnvo() {
        return txnvo;
    }

    public String getTxnCode() {
		return txnCode;
	}

	public void setTxnCode(String txnCode) {
		this.txnCode = txnCode;
	}

	public void setTxnvo(Object txnvo) {
        this.txnvo = txnvo;
    }
}
