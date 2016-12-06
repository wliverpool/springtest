package com.pojo;

import java.util.HashMap;

/**
 * 用于存储正在通过二维码登录用户,一旦完成登录从map中移除
 * @author 吴福明
 *
 */
public class QrCodeLoginUserVo {

	private static HashMap<String, UserVo> loginUserMap = new HashMap<String, UserVo>();

	private static QrCodeLoginUserVo loginUserVo;

	public static QrCodeLoginUserVo getVo() {
		if (loginUserVo == null) {
			loginUserVo = new QrCodeLoginUserVo();
		}
		return loginUserVo;

	}

	public static HashMap<String, UserVo> getLoginUserMap() {
		return loginUserMap;
	}

}
