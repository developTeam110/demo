package com.demo.common.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.back.po.User;
import com.demo.common.constant.CommEnum;
import com.demo.common.constant.CookieConstant;
import com.demo.common.constant.SecretKeyConstant;
import com.demo.common.util.EncryptUtil;
import com.demo.common.util.StringUtil;

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
	 */
	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		Cookie cookie = CookieHelper.getCookie(request, cookieName);
		if (cookie != null) {
			return cookie.getValue();
		}
		return null;
	}

	/**
	 * 添加用户COOKICE
	 */
	public static void addUserCookie(HttpServletRequest request, HttpServletResponse response, User user) {
		// 设置页面不缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("P3P", "CP=CAO PSA OUR");
		response.setDateHeader("Expires", 0);

		//保存token信息
		String tokenCookieValue = EncryptUtil.encodeCookieValue(user.getUsername(), SecretKeyConstant.COOKIE_SECRET_KEY);
		Cookie tokenCookie = new Cookie(CookieConstant.COOKIE_TOKEN, tokenCookieValue);
		//tokenCookie.setDomain(cookiceDomain);// 替换cookie域名
		tokenCookie.setPath(CookieConstant.COOKIE_PATH);
		tokenCookie.setMaxAge(CommEnum.TIME_SECONDS.ONE_WEEK.sec());
		response.addCookie(tokenCookie);

		//保存昵称信息
		String nicknameCookieValue = EncryptUtil.encodeURL(user.getNickname());
		if (StringUtil.isNotEmpty(nicknameCookieValue)) {
			Cookie nicknameCookie = new Cookie(CookieConstant.COOKIE_NICKNAME, nicknameCookieValue);
			//nicknameCookie.setDomain(cookiceDomain);// 替换cookie域名
			nicknameCookie.setPath(CookieConstant.COOKIE_PATH);
			response.addCookie(nicknameCookie);
		}

		//保存头像信息
		if (StringUtil.isNotEmpty(user.getHeadImage())) {
			String headImageCookieValue = EncryptUtil.encodeURL(user.getHeadImage());
			Cookie headImageCookie = new Cookie(CookieConstant.COOKIE_HEAD_IMAGE, headImageCookieValue);
			//headImageCookie.setDomain(cookiceDomain);// 替换cookie域名
			headImageCookie.setPath(CookieConstant.COOKIE_PATH);
			response.addCookie(headImageCookie);
		}
	}

	/**
	 * 获取用户登录COOKICE
	 */
	public static User getUserCookie(HttpServletRequest request) {
		if (request == null) {
			return null;
		}

		User user = new User();

		//用户名
		String token = getCookieValue(request, CookieConstant.COOKIE_TOKEN);
		if (StringUtil.isNotEmpty(token)) {
			String username = UserHelper.tokenToUsername(token);
			user.setUsername(username);
		}

		//昵称信息
		String encrNickname = getCookieValue(request, CookieConstant.COOKIE_NICKNAME);
		if (StringUtil.isNotEmpty(encrNickname)) {
			String nickname = EncryptUtil.decodeURL(encrNickname);
			user.setNickname(nickname);
		}

		//头像信息
		String encrheadImage = getCookieValue(request, CookieConstant.COOKIE_HEAD_IMAGE);
		if (StringUtil.isNotEmpty(encrheadImage)) {
			String headImage = EncryptUtil.decodeURL(encrheadImage);
			user.setHeadImage(headImage);
		}

		return user;
	}

	/**
	 * 删除登录cookie
	 */
	public static void delLoginCookie(HttpServletRequest request, HttpServletResponse response) {
		//String cookiceDomain = FilterUtil.checkDomain(request.getServerName());//COOKIE_DOMAIN

		Cookie tokenCookie = new Cookie(CookieConstant.COOKIE_TOKEN, null);
		tokenCookie.setMaxAge(0);
		//slCookie.setDomain(cookiceDomain);//替换cookie域名
		tokenCookie.setPath(CookieConstant.COOKIE_PATH);
		response.addCookie(tokenCookie);

		Cookie nicknameCookie = new Cookie(CookieConstant.COOKIE_NICKNAME, null);
		nicknameCookie.setMaxAge(0);
		//slCookie.setDomain(cookiceDomain);//替换cookie域名
		nicknameCookie.setPath(CookieConstant.COOKIE_PATH);
		response.addCookie(nicknameCookie);

		Cookie headImageCookie = new Cookie(CookieConstant.COOKIE_HEAD_IMAGE, null);
		headImageCookie.setMaxAge(0);
		//slCookie.setDomain(cookiceDomain);//替换cookie域名
		headImageCookie.setPath(CookieConstant.COOKIE_PATH);
		response.addCookie(headImageCookie);
	}

}
