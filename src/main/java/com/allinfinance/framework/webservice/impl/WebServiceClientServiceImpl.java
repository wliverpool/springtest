package com.allinfinance.framework.webservice.impl;


import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.allinfinance.framework.dto.OperationCtx;
import com.allinfinance.framework.dto.OperationRequest;
import com.allinfinance.framework.dto.OperationResult;
import com.allinfinance.framework.exception.BizServiceException;
import com.allinfinance.framework.util.Config;
import com.allinfinance.framework.util.XstreamDateConverter;
import com.allinfinance.framework.webservice.ManageService;
import com.allinfinance.framework.webservice.service.WebServiceClientService;
import com.thoughtworks.xstream.XStream;


/**
 * <p>
 * Title: Accor
 * </p>
 * 
 * <p>
 * Description:Accor Project 1nd Edition
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
@Service("webServiceClientService")
public class WebServiceClientServiceImpl implements WebServiceClientService {
	private final static Logger logger = LoggerFactory.getLogger(WebServiceClientServiceImpl.class);
	protected static final String NEED = "1";
	protected static final String NOTNEED = "0";
	protected String needPwdCheck;
	protected String needRecord;

	private ManageService manageService;

	public ManageService getManageService() {
		return manageService;
	}

	public void setManageService(ManageService manageService) {
		this.manageService = manageService;
	}

	/**
	 * 
	 */
	public OperationResult process(OperationCtx ctx, OperationRequest req)
			throws BizServiceException {
		String strCtx = getReqXmlStr(ctx);
		String strReq = getReqXmlStr(req);
		logger.info(strCtx);
		logger.info(strReq);

		OperationResult operationResult = new OperationResult();
		try {
            if(manageService == null){
            	 JaxWsProxyFactoryBean svr = new JaxWsProxyFactoryBean();
                 svr.setServiceClass(ManageService.class);
                 //svr.setAddress(Config.getWebserviceAddress());
                 svr.setAddress("http://10.250.3.82:8080/ShangQiGateWayService/manageService");
                 manageService = (ManageService) svr.create();
             }     
			String strRsp = manageService.sendServece(strCtx, strReq);
			operationResult = getRspToXml(strRsp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizServiceException("无法连接到后台服务器");
			
		}
		return operationResult;
	}

	/**
	 * 获得一个用户请求对象的xml字符�?
	 * 
	 * @param obj
	 * @return
	 */
	public String getReqXmlStr(Object obj) {

		XStream smReq = new XStream();
		smReq.registerConverter(new XstreamDateConverter());
		smReq.aliasPackage("REQ", "com.allinfinance.");
		smReq.registerConverter(new XstreamDateConverter());
		return smReq.toXML(obj);
	}
	/**
	 * 将一个xml字符串转换为OperationResult对象
	 * @param strRsp
	 * @return
	 */
	public OperationResult getRspToXml(String strRsp) {
		OperationResult operationResult = null;
		
		try {
			XStream smRsp = new XStream();
			smRsp.registerConverter(new XstreamDateConverter());
			smRsp.alias("OperationResult", OperationResult.class);
			smRsp.aliasPackage("RSP", "com.allinfinance.");
			operationResult = (OperationResult) smRsp
					.fromXML(strRsp);
		} catch (Exception e)  {
			e.printStackTrace();
		}
		return operationResult;
	}

}
