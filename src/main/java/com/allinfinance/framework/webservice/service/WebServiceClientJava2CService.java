package com.allinfinance.framework.webservice.service;

import com.allinfinance.framework.dto.OperationCtx;
import com.allinfinance.framework.dto.OperationRequest;
import com.allinfinance.framework.dto.OperationResult;
import com.allinfinance.framework.exception.BizServiceException;

public interface WebServiceClientJava2CService {
	/**
     * <p>Description: interface for webservice</p>
     * 
     * @param ctx 由OperationCtx对象转换得出的xml格式字符串
     *        req 由OperationRequest对象转换得出的xml格式字符串
     * @return 由OperationResult对象转换得出的xml格式字符串
     * @exception 无异常
     */
	public OperationResult tpRequest(OperationCtx ctx, OperationRequest req) throws BizServiceException;

	/**
     * <p>Description: interface for webservice</p>
     * 
     * @param ctx 由OperationCtx对象转换得出的xml格式字符串
     *        req 由OperationRequest对象转换得出的xml格式字符串
     * @return 由OperationResult对象转换得出的xml格式字符串
     * @exception 无异常
     */
	public OperationResult tpaRequest(OperationCtx ctx, OperationRequest req) throws BizServiceException;
}
