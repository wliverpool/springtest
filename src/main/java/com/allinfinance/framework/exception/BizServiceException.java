package com.allinfinance.framework.exception;

import java.util.List;

public class BizServiceException extends Exception {
	

	public BizServiceException(String errorMessage, Exception e) {
		this.errorMessage = errorMessage;
		this.setStackTrace(e.getStackTrace());
	}

	public BizServiceException(String errorMessage) {

		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public List getErrors() {
		return errors;
	}

	public BizServiceException(List errors) {
		this.errors = errors;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6449274692836915363L;

	private String errorMessage;

	private List errors;
	

}
