package com.demo.common.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.demo.common.constant.SecretKeyConstant;

public class PhoneUtil {

	private static final String PHONE_REG = "^((1[0,3,5,8][0-9])|(14[5,7])|(17[0,1,3,6,7,8]))\\d{8}$";
	private static final String PHONE_TWO_DIGIT = "34578";
	private static final Integer PHONE_NUMBER_LENGTH = 11;

	/**
	 * 是否为有效手机号
	 */
	public static boolean isValid(String phone) {
		if (StringUtil.isEmpty(phone)) {
			return false;
		}

		Pattern pwdPattern = Pattern.compile(PHONE_REG);
		return pwdPattern.matcher(phone).matches();
	}

	/**
	 * 模糊手机的方法
	 * @param phone 手机号
	 */
	public static String fuzzy(String phone) {
		if (StringUtil.isEmpty(phone)) {
			return "";
		}

		Pattern pattern = Pattern.compile(PHONE_REG);
		if (pattern.matcher(phone).matches()) {
			return phone.substring(0,3) + "****" + phone.substring(7, 11);
		} else {
			return phone;
		}
	}

	/** 
	* REVIEW
	* @Description: 获取一个随机手机号码
	* @return 随机手机号码
	*/
	public static String getRandomPhoneNumber() {
		StringBuilder sb = new StringBuilder("1");

		Random random = new Random();
		sb.append(PHONE_TWO_DIGIT.charAt(random.nextInt(PHONE_TWO_DIGIT.length())));

		String tailNumber = RandomUtil.generateString(PHONE_NUMBER_LENGTH - 2);
		sb.append(tailNumber);
		String phoneNumber = sb.toString();

		Matcher matcher = Pattern.compile(PHONE_REG).matcher(phoneNumber);
		if (matcher.matches()) {
			return sb.toString();
		} else {
			return getRandomPhoneNumber();
		}
	}

	/**
	 * 对手机号码加密
	 * @param phone 明文手机号
	 */
	public static String encodeByAes(String phone) {
		return AESEncryptUtil.assemble(phone, SecretKeyConstant.PHONE_SECRET_KEY);
	}

	/**
	 * 对手机号码解密
	 * @param aesPhone AES加密过的手机号
	 */
	public static String decodeByAes(String aesPhone) {
		return AESEncryptUtil.disassemble(aesPhone, SecretKeyConstant.PHONE_SECRET_KEY);
	}
}
