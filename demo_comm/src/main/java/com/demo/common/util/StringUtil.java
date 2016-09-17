package com.demo.common.util;

import java.util.UUID;
import java.util.regex.Pattern;

public class StringUtil {

	private static final String passwordReg = "^\\w{6,16}$";//必须是6到16个英文字符、数字
	private static final String loginNameReg = "^\\w{4,16}$";//4到16个英文字符、数字或下划线

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
	 * 是否为有效的密码（必须是6到16个英文字符、数字）
	 */
	public static boolean isValidPassword(String password) {
		if (password == null) {
			return false;
		}

		Pattern pwdPattern = Pattern.compile(passwordReg);
		return pwdPattern.matcher(password).matches();
	}

	/**
	 * 是否为有效的登录名（必须是6到16个英文字符、数字）
	 */
	public static boolean isValidLoginName(String loginName) {
		if (loginName == null) {
			return false;
		}

		Pattern pwdPattern = Pattern.compile(loginNameReg);
		return pwdPattern.matcher(loginName).matches();
	}

	/**
	 * 通过UUID生成字符串（去掉字符 -）
	 */
	public static String getRandomStrByUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
