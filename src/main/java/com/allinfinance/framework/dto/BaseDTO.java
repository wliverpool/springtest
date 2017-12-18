package com.allinfinance.framework.dto;

/**
 * 共通DTO
 * @author dawn
 *
 */
public class BaseDTO  implements java.io.Serializable{

   
	private static final long serialVersionUID = -2018516784338711432L;

	/**
     * 登录用户ID
     */
    private String loginUserId;

    
    /***
     * 默认实体ID
     * @return
     */
    
    private String defaultEntityId;


	public String getLoginUserId() {
		return loginUserId;
	}


	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}


	public String getDefaultEntityId() {
		return defaultEntityId;
	}


	public void setDefaultEntityId(String defaultEntityId) {
		this.defaultEntityId = defaultEntityId;
	}
    
    
    
}
