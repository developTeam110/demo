package com.demo.common.util;

import java.util.UUID;
import java.util.regex.Pattern;

public class StringUtil {

	private static final String passwordReg = "^\\w{6,16}$";//必须是6到16个英文字符、数字
	private static final String loginNameReg = "^\\w{4,16}$";//4到16个英文字符、数字或下划线
	private static final String emailReg = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	private static final String phoneReg = "^((1[0,3,5,8][0-9])|(14[5,7])|(17[0,1,3,6,7,8]))\\d{8}$";

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
	 * 是否为有效邮箱
	 */
	public static boolean isValidEmail(String email) {
		if (email == null) {
			return false;
		}

		Pattern pwdPattern = Pattern.compile(emailReg);
		return pwdPattern.matcher(email).matches();
	}

	/**
	 * 是否为有效邮箱
	 */
	public static boolean isValidPhone(String phone) {
		if (phone == null) {
			return false;
		}

		Pattern pwdPattern = Pattern.compile(phoneReg);
		return pwdPattern.matcher(phone).matches();
	}

	/**
	 * 通过UUID生成字符串（去掉字符 -）
	 */
	public static String generateRandomUsernameByUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
