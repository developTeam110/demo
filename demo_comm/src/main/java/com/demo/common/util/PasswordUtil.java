package com.demo.common.util;

import java.util.regex.Pattern;

import com.demo.common.constant.SecretKeyConstant;

public class PasswordUtil {
	private static final String PASSWORD_REG = "^\\w{6,16}$";//必须是6到16个英文字符、数字

	/**
	 * 是否为有效的密码（必须是6到16个英文字符、数字）
	 */
	public static boolean isValid(String password) {
		if (StringUtil.isEmpty(password)) {
			return false;
		}

		Pattern pwdPattern = Pattern.compile(PASSWORD_REG);
		return pwdPattern.matcher(password).matches();
	}

	/**
	 * 对密码加密
	 * @param password 明文密码
	 */
	public static String encodeByMd5(String password) {
		if (StringUtil.isEmpty(password)) {
			return null;
		}

		return EncryptUtil.encodeByMd5(password, SecretKeyConstant.PASSWORD_SECRET_KEY);
	}
}
