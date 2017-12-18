package com.allinfinance.framework.webservice.service;

import org.springframework.stereotype.Service;

import com.allinfinance.framework.dto.OperationCtx;
import com.allinfinance.framework.dto.OperationRequest;
import com.allinfinance.framework.dto.OperationResult;
import com.allinfinance.framework.exception.BizServiceException;


/**
 * <p>Title: Accor</p>
 *
 * <p>Description:Accor Project 1nd Edition </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 * @author YY
 * @version 1.0
 */
@Service
public interface WebServiceClientService {
	
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
