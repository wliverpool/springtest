package com.allinfinance.framework.webservice;

import javax.jws.WebService;

import com.allinfinance.framework.exception.BizServiceException;

@WebService
public interface Java2CService {
	/**
	 * 同步发送请求
	 * @param ctx
	 * @param req
	 * @return
	 */
	public String tpRequest(String ctx, String req) throws BizServiceException ;
	
	/**
	 * 异步发送请求
	 * @param ctx
	 * @param req
	 * @return
	 * @throws BizServiceException
	 */
	public String tpaRequest(String ctx, String req) throws BizServiceException ;
}
