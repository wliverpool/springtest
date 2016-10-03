package com.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 处理ajax请求的springmvc的base controller
 * @author 吴福明
 *
 */

public class AjaxAction {
	
	protected static final Log LOG = LogFactory.getLog(AjaxAction.class);

	// AJAX输出，返回null
	public String ajax(String content, String type, HttpServletResponse response) {
		try {
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			LOG.error("ajax", e);
		}
		return null;
	}

	/** AJAX输出HTML，返回null **/
	public String ajaxHtml(String html, HttpServletResponse response) {
		return ajax(html, "text/html", response);
	}

	/** AJAX输出json，返回null **/
	public String ajaxJson(String json, HttpServletResponse response) {
		return ajax(json, "application/json", response);
	}

}
