package com.demo.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.common.helper.CookieHelper;


public class EncryptUtil {

	private static final Logger logger = LoggerFactory.getLogger(EncryptUtil.class);

	/**
	 * 利用MD5进行加密
	 */
	public static String encodeByMd5(String str) {
		if (str == null) {
			return "";
		}

		return DigestUtils.md5Hex(str);
	}

	public static String EncodeByRC4(String aInput, String aKey) {
		int[] iS = new int[256];
		byte[] iK = new byte[256];

		for (int i = 0; i < 256; i++)
			iS[i] = i;

		int j = 1;

		for (short i = 0; i < 256; i++) {
			iK[i] = (byte) aKey.charAt((i % aKey.length()));
		}

		j = 0;

		for (int i = 0; i < 255; i++) {
			j = (j + iS[i] + iK[i]) % 256;
			int temp = iS[i];
			iS[i] = iS[j];
			iS[j] = temp;
		}

		int i = 0;
		j = 0;
		char[] iInputChar = aInput.toCharArray();
		char[] iOutputChar = new char[iInputChar.length];
		for (short x = 0; x < iInputChar.length; x++) {
			i = (i + 1) % 256;
			j = (j + iS[i]) % 256;
			int temp = iS[i];
			iS[i] = iS[j];
			iS[j] = temp;
			int t = (iS[i] + (iS[j] % 256)) % 256;
			int iY = iS[t];
			char iCY = (char) iY;
			iOutputChar[x] = (char) (iInputChar[x] ^ iCY);
		}

		return new String(iOutputChar);
	}

	/**
	 * 对cookie的值编码
	 * @param aInput cookie值
	 * @param aKey 编码的字符串
	 * @return
	 */
	public static String encodeCookieValue(String aInput, String aKey) {
		String str = EncodeByRC4(aInput, aKey);
		String base64Str = "";
		try {
			base64Str = Base64.encodeBase64String(ConvertUtil.getBytesFromObject(str));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return base64Str.replace("\r\n", "");
	}

	/**
	 * 对cookie的值解码
	 * @param base64Str cookie值
	 * @param aKey 解码字符串
	 * @return 解码后的值
	 */
	public static String decodeCookieValue(String base64Str, String aKey) {
		String str = "";
		try {
			str = EncodeByRC4(ConvertUtil.getObjectFromBytes(Base64.decodeBase64(base64Str)).toString(), aKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

}
