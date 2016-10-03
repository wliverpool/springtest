package com.service;

import java.util.HashMap;
import java.util.Map;

import com.util.HttpUtil;

public class SendSMSSerivce {
	
	// 短信接口URL提交地址
	public static String SMSURL = "";

	/**
	 * 发送短信消息
	 * 
	 */
	public static String sendMsg(String phones, String content) {

		Map<String, String> params = new HashMap<String, String>();

		//params.put(zh, 用户账号);
		//params.put(mm, 用户密码);
		//params.put(dxlbid, 短信类别编号);
		//params.put(extno, 扩展编号);

		// 手机号码
		//params.put(hm, phones);
		// 将短信内容进行URLEncoder编码
		//params.put(nr, URLEncoder.encode(content));

		return HttpUtil.getResponseByHttpPost(SMSURL,params);
	}

	/**
	 * 随机生成6位随机验证码
	 */
	public static String createRandomVcode() {
		// 验证码
		String vcode = "";
		for (int i = 0; i < 6; i++) {
			vcode = vcode + (int) (Math.random() * 9);
		}
		return vcode;
	}

    public static void main(String[] args) {
//      System.out.println(SendMsgUtil.createRandomVcode());
//      System.out.println(&ecb=12.substring(1));
        System.out.println(sendMsg("18123456789", "尊敬的用户，您的验证码为 "+ SendSMSSerivce.createRandomVcode() + "，有效期为60秒，如有疑虑请详询400-069-2886（客服电话）【XXX中心】"));
    }

}
