package com.demo.common.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.common.constant.CookieConstant;
import com.demo.common.util.EncryptUtil;
import com.demo.common.util.StringUtil;

public class UserHelper {

	private static final Logger logger = LoggerFactory.getLogger(CookieHelper.class);

	/**
	 * 获取用户的Ip地址
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if(StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP"); 
		}
		if(StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP"); 
		}
		if(StringUtil.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip; 
	}

	/**
	 * 获取登录用户的用户名
	 * @param request 请求对象
	 * @return 用户名
	 */
	public static String getLoginedUsername(HttpServletRequest request) {
		String username = CookieHelper.getLoginCookieValue(request);
		if (StringUtil.isNotEmpty(username)) {
			return username;
		}

		return getLoginedUsernameByToken(request.getParameter("token"));
	}

	/**
	 * 通过token获取登录用户的用户名
	 * @param token 用户标记
	 * @return 用户用户名
	 */
	public static String getLoginedUsernameByToken(String token) {
		if (StringUtil.isEmpty(token)) {
			return null;
		}

		//token可能有特殊字符，前端encode了，这里需要decode一下
		String urlEncodedUsername = "";
		try {
			urlEncodedUsername = URLDecoder.decode(token, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			logger.error("getLoginedUserByToken decode encodedUsername error!", e1);
		}

		String userName = null;
		//先根据urlEncodedUsername解
		try {
			userName = EncryptUtil.decodeCookieValue(urlEncodedUsername, CookieConstant.COOKIE_KEY);
			logger.info("decode username is [{}]", userName);
		} catch (Exception e1) {
			logger.error("getLoginedUser encounter an decode cookie exception : ", e1);
		}

		//根据encodedUsername解
		if (StringUtil.isEmpty(userName)) {
			try {
				userName = EncryptUtil.decodeCookieValue(token, CookieConstant.COOKIE_KEY);

			} catch (Exception e1) {
				logger.error("getLoginedUser encounter an decode cookie exception : ", e1);
			}
		}

		return userName;
	}

}
