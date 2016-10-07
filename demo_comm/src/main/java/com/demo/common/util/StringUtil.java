package com.demo.common.util;

import java.util.UUID;
import java.util.regex.Pattern;

public class StringUtil {

	private static final String PASSWORD_REG = "^\\w{6,16}$";//必须是6到16个英文字符、数字
	private static final String LOGINNAME_REG = "^\\w{4,16}$";//4到16个英文字符、数字或下划线
	private static final String EMAIL_REG = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	private static final String PHONE_REG = "^((1[0,3,5,8][0-9])|(14[5,7])|(17[0,1,3,6,7,8]))\\d{8}$";

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

		Pattern pwdPattern = Pattern.compile(PASSWORD_REG);
		return pwdPattern.matcher(password).matches();
	}

	/**
	 * 是否为有效的登录名（必须是6到16个英文字符、数字）
	 */
	public static boolean isValidLoginName(String loginName) {
		if (loginName == null) {
			return false;
		}

		Pattern pwdPattern = Pattern.compile(LOGINNAME_REG);
		return pwdPattern.matcher(loginName).matches();
	}

	/**
	 * 是否为有效邮箱
	 */
	public static boolean isValidEmail(String email) {
		if (email == null) {
			return false;
		}

		Pattern pwdPattern = Pattern.compile(EMAIL_REG);
		return pwdPattern.matcher(email).matches();
	}

	/**
	 * 是否为有效邮箱
	 */
	public static boolean isValidPhone(String phone) {
		if (phone == null) {
			return false;
		}

		Pattern pwdPattern = Pattern.compile(PHONE_REG);
		return pwdPattern.matcher(phone).matches();
	}

	/**
	 * 通过UUID生成字符串（去掉字符 -）
	 */
	public static String generateRandomUsernameByUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 屏蔽手机的方法
	 * @param source 手机号
	 * @return
	 */
	public static String hiddenMobile(String source) {
		if (isEmpty(source)) {
			return "";
		}

		Pattern pattern = Pattern.compile(PHONE_REG);
		if (pattern.matcher(source).matches()) {
			return source.substring(0,3) + "****" + source.substring(7, 11);
		} else {
			return source;
		}
		
	}
	
	
	/**
	 * 屏蔽邮箱的方法
	 * @param source 邮箱
	 * @return
	 */
	public static String hiddenEmail(String source) {
		if (isEmpty(source)) {
			return "";
		}

		Pattern pattern = Pattern.compile(EMAIL_REG);
		if (pattern.matcher(source).matches()) {
			int splitIndex = source.indexOf("@");
			String emailPrefix = source.substring(0, splitIndex);
			if(emailPrefix.length() > 4) {
				source = source.substring(0, emailPrefix.length()-4) + "****" + source.substring(splitIndex);
			} else {
				source = source.substring(0, 1) + "****" + source.substring(splitIndex);
			}
			return source;
		} else {
			return source;
		}
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
