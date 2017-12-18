package com.allinfinance.framework.webservice.service;

import com.allinfinance.framework.dto.OperationCtx;
import com.allinfinance.framework.dto.OperationRequest;
import com.allinfinance.framework.dto.OperationResult;
import com.allinfinance.framework.exception.BizServiceException;

public interface WebServiceClientRptService {
	/**
     * <p>Description: interface for webservice</p>
     * 
     * @param ctx 由OperationCtx对象转换得出的xml格式字符串
     *        req 由OperationRequest对象转换得出的xml格式字符串
     * @return 由OperationResult对象转换得出的xml格式字符串
     * @exception 无异常
     */
	public OperationResult process(OperationCtx ctx, OperationRequest req) throws BizServiceException;
}
