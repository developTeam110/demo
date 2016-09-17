package com.demo.common.helper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.back.po.User;
import com.demo.common.constant.CommEnum;
import com.demo.common.constant.CookieConstant;
import com.demo.common.util.EncryptUtil;

public class CookieHelper {

	private static final Logger logger = LoggerFactory.getLogger(CookieHelper.class);

	/**
	 * 获取cookie对象
	 * @param request 请求对象
	 * @param cookieName 名称
	 * @return cookie对象
	 */
	public static Cookie getCookie(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie c = cookies[i];
				if (c.getName().equalsIgnoreCase(cookieName)) {
					return c;
				}
			}
		}
		return null;
	}

	/**
	 * 获取登录的cookie值
	 * @param request 请求对象
	 * @return 登录cookie值
	 */
	public static String getLoginCookieValue(HttpServletRequest request) {
		Cookie cookie = CookieHelper.getCookie(request, CookieConstant.LOGIN_COOKIE);
		if (cookie != null) {
			return cookie.getValue();
		}
		return null;
	}

	/**
	 * 添加用户COOKICE
	 */
	public static String addLoginCookie(HttpServletRequest request, HttpServletResponse response, User user) {
		// 设置页面不缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("P3P", "CP=CAO PSA OUR");
		response.setDateHeader("Expires", 0);

		final String cookieValue = EncryptUtil.encodeCookieValue(user.getUsername(), CookieConstant.COOKIE_KEY);
		Cookie loginCookie = new Cookie(CookieConstant.COOKIE_USERNAME, cookieValue);
		//loginCookie.setDomain(cookiceDomain);// 替换cookie域名
		loginCookie.setPath(CookieConstant.COOKIE_PATH);
		loginCookie.setMaxAge(CommEnum.TIME_SECONDS.ONE_WEEK.sec());
		response.addCookie(loginCookie);
		return cookieValue;
	}

	/**
	 * 删除登录cookie
	 */
	public static void delLoginCookie(HttpServletRequest request, HttpServletResponse response) {
		//String cookiceDomain = FilterUtil.checkDomain(request.getServerName());//COOKIE_DOMAIN

		Cookie slCookie = new Cookie(CookieConstant.COOKIE_USERNAME, null);
		slCookie.setMaxAge(0);
		//slCookie.setDomain(cookiceDomain);//替换cookie域名
		slCookie.setPath(CookieConstant.COOKIE_PATH);
		response.addCookie(slCookie);
	}

}
