package com.allinfinance.framework.webservice;

import javax.jws.WebService;

@WebService
public interface ReportService {
	/**
	 * 
	 * @param ctx
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public String sendServece(String ctx, String req) ;
}
