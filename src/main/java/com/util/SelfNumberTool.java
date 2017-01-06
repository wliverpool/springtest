package com.util;

/**
 * 自定义velocity工具
 * @author 吴福明
 *
 */
public class SelfNumberTool {

	/**
	 * 将浮点数小数，固定保留两位小数
	 */
	public static String toFixedNumber(Double d) {
		if (d == null) {
			return "";
		}
		String valStr = String.format("%1$.2f", d);
		return valStr;
	}

}
