package com.demo.common.util;

import java.util.UUID;

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

	/**
	 * 通过UUID生成字符串（去掉字符 -）
	 */
	public static String generateRandomUsernameByUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 屏蔽身份证号的方法
	 * @param source 身份证号
	 */
	public static String hiddenIDCard(String source) {
		if (isEmpty(source)) {
			return "";
		}

		if(source.length() > 8) {
			source = source.substring(0, source.length()-8) + "********";
		} else{
			source = source.substring(0, 1) + "********";
		}
		return source;
	}
}
