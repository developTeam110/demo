package com.demo.common.util;

import java.util.regex.Pattern;

public class LoginNameUtil {

	private static final String LOGINNAME_REG = "^\\w{4,16}$";//4到16个英文字符、数字或下划线

	/**
	 * 是否为有效的登录名（必须是6到16个英文字符、数字，不可以是电话号码或邮箱格式）
	 */
	public static boolean isValid(String loginName) {
		if (StringUtil.isEmpty(loginName)) {
			return false;
		}

		Pattern pwdPattern = Pattern.compile(LOGINNAME_REG);
		return pwdPattern.matcher(loginName).matches() && !PhoneUtil.isValid(loginName) && !EmailUtil.isValid(loginName);
	}
}
