package com.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.util.BigWheelDrawUtil;
import com.util.JsonUtil;

import net.sf.json.JSONObject;

/**
 * 
 * 功能描述：大转盘controller类<br>
 * 
 * @author 吴福明
 */

@Controller
@RequestMapping("/bigWheel")
public class BigWheelActivityAction extends AjaxAction{

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 抽奖
	 * @param mobile 抽奖手机号
	 * @return
	 */
	@RequestMapping("/MarkLuckDraw")
	@ResponseBody
	public String markLuckDraw(HttpServletRequest request, HttpServletResponse response,String mobile) {
		JSONObject returnJson = new JSONObject();
		// 生成中奖金额对象
		Map<String, String> result = BigWheelDrawUtil.generateAward();
		if (result == null) {
			returnJson.put("result", "3");
			returnJson.put("errorMessage", "生成抽奖数据失败！");
			return returnJson.toString();
		}
		returnJson.put("result", result);
		if (logger.isErrorEnabled()) {
			logger.error("微信分享活动：手机号码为：{}，中奖信息：{}", mobile, JSONObject.fromObject(result).toString());
		}

		// 写入中间信息
		return ajaxJson(JsonUtil.getJsonString4JavaPOJO(returnJson), response);
	}

}
