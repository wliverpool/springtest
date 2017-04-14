package com.util;

import java.io.Serializable;
import java.util.Map;

/**
 * springmvc手工验证结果类
 * @author 吴福明
 *
 */
public class ValidateResult implements Serializable{
	
	private static final long serialVersionUID = 3599626105669785936L;
	
	//是否有错误
	private boolean hasErrors;
	//错误信息map
	private Map<String,String> errorMsgMap;

	public boolean isHasErrors() {
		return hasErrors;
	}

	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}

	public Map<String, String> getErrorMsgMap() {
		return errorMsgMap;
	}

	public void setErrorMsgMap(Map<String, String> errorMsgMap) {
		this.errorMsgMap = errorMsgMap;
	}

}
