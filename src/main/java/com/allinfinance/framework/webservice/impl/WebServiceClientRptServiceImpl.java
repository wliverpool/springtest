package com.allinfinance.framework.webservice.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.allinfinance.framework.dto.OperationCtx;
import com.allinfinance.framework.dto.OperationRequest;
import com.allinfinance.framework.dto.OperationResult;
import com.allinfinance.framework.exception.BizServiceException;
import com.allinfinance.framework.webservice.ReportService;
import com.allinfinance.framework.webservice.service.WebServiceClientRptService;
import com.thoughtworks.xstream.XStream;

public class WebServiceClientRptServiceImpl implements
		WebServiceClientRptService {
	private final static Logger logger = LoggerFactory.getLogger(WebServiceClientRptServiceImpl.class);
	protected static final String NEED = "1";
	protected static final String NOTNEED = "0";
	protected String needPwdCheck;
	protected String needRecord;
	private ReportService reportService;
	
	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public OperationResult process(OperationCtx ctx, OperationRequest req)
			throws BizServiceException {
		
		String strCtx = getReqXmlStr(ctx);
		String strReq = getReqXmlStr(req);

		OperationResult operationResult = new OperationResult();
		try {
			String strRsp = reportService.sendServece(strCtx, strReq);
			operationResult = getRspToXml(strRsp);
		} catch (Exception e) {
			throw new BizServiceException("无法连接到后台服务器");
		}
		return operationResult;
	}
	/**
	 * 获得一个用户请求对象的xml字符串
	 * 
	 * @param obj
	 * @return
	 */
	public String getReqXmlStr(Object obj) {

		XStream smReq = new XStream();
		smReq.aliasPackage("REQ", "com.allinfinance.");
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
			smRsp.alias("OperationResult", OperationResult.class);
			smRsp.aliasPackage("RSP", "com.allinfinance.");
			operationResult = (OperationResult) smRsp
					.fromXML(strRsp);
		} catch (Exception e)  {
			this.logger.error(e.getMessage());
		}
		return operationResult;
	}
}
