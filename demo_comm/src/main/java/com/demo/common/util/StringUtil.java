package com.demo.common.util;

public class StringUtil {

	/**
	 * 判断传入的字符串是否为空串
	 */
	public static boolean isEmpty(String str) {
		return str == null ? true : str.trim().equals("") ? true : false;
	}

	/**
	 * 判断传入的字符串是否为空串
	 */
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}
}
