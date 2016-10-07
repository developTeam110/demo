package com.demo.common.util;

import java.util.Random;

public class RandomUtil {

	public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String numberChar = "0123456789";
	public static final String codeChar = "abcdefghigklmnopkrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";

	/**
	  *   产生四位随机数
	  *   @version   1.0
	  *   @author
	  */

	public String getRandom() {
		Random rd = new Random(); // 创建随机对象
		String num = " ";
		int rdGet; // 取得随机数
		do {
			rdGet = Math.abs(rd.nextInt()) % 10 + 48; // 产生48到57的随机数(0-9的键位值)
			// rdGet=Math.abs(rd.nextInt())%26+97; //产生97到122的随机数(a-z的键位值)
			char num1 = (char) rdGet;
			String dd = Character.toString(num1);
			num += dd;
		} while (num.length() <= 6); // 长度6

		return num;
	}

	public static String generateString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
		}
		return sb.toString();
	}

	public static String generateCodeString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(codeChar.charAt(random.nextInt(codeChar.length())));
		}
		return sb.toString();
	}

	public static String generateNumCodeString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(allChar.charAt(random.nextInt(allChar.length())));
		}
		return sb.toString();
	}

	public static String generatePasswd() {
		return generateString(6);
	}

	public static void main(String[] args) {
		System.out.println(new RandomUtil().getRandom());
		System.out.println(generateString(10));
		System.out.println(generateCodeString(10));
		System.out.println(generateNumCodeString(10));
		String name = RandomUtil.generateString(10) + RandomUtil.generateCodeString(2);
		System.out.println(name);
	}
}
