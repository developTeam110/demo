package com.demo.common.util;

import java.util.regex.Pattern;

public class EmailUtil {

	private static final String EMAIL_REG = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

	/**
	 * 是否为有效邮箱
	 */
	public static boolean isValid(String email) {
		if (email == null) {
			return false;
		}

		Pattern pwdPattern = Pattern.compile(EMAIL_REG);
		return pwdPattern.matcher(email).matches();
	}

	/**
	 * 模糊邮箱的方法
	 * @param email 邮箱
	 */
	public static String fuzzy(String email) {
		if (StringUtil.isEmpty(email)) {
			return "";
		}

		Pattern pattern = Pattern.compile(EMAIL_REG);
		if (pattern.matcher(email).matches()) {
			int splitIndex = email.indexOf("@");
			String emailPrefix = email.substring(0, splitIndex);
			if(emailPrefix.length() > 4) {
				email = email.substring(0, emailPrefix.length()-4) + "****" + email.substring(splitIndex);
			} else {
				email = email.substring(0, 1) + "****" + email.substring(splitIndex);
			}
			return email;
		} else {
			return email;
		}
	}
}
