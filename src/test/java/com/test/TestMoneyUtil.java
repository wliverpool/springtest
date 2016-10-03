package com.test;

import com.util.MoneyUtil;

public class TestMoneyUtil {
	
	public static void main(String[] args) {
		String number = "1.23";
		System.out.println(number + " " + MoneyUtil.toChinese(number));
		number = "1234567890123456.123";
		System.out.println(number + " " + MoneyUtil.toChinese(number));
		number = "0.0798";
		System.out.println(number + " " + MoneyUtil.toChinese(number));
		number = "10,001,000.09";
		System.out.println(number + " " + MoneyUtil.toChinese(number));
		number = "01.107700";
		System.out.println(number + " " + MoneyUtil.toChinese(number));
	}

}
