package com.demo.common.helper;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.demo.back.po.User;
import com.demo.back.service.UserCacheService;
import com.demo.common.constant.CookieConstant;
import com.demo.common.constant.SecretKeyConstant;
import com.demo.common.util.EncryptUtil;
import com.demo.common.util.StringUtil;

public class UserHelper {

	private static final Logger logger = LoggerFactory.getLogger(CookieHelper.class);

	@Autowired
	private static UserCacheService userCacheService;

	public void setUserCacheService(UserCacheService userCacheService) {
		UserHelper.userCacheService = userCacheService;
	}

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
		String token = CookieHelper.getCookieValue(request, CookieConstant.COOKIE_TOKEN);
		if (StringUtil.isNotEmpty(token)) {
			return tokenToUsername(token);
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

		String username = tokenToUsername(token);
		if (StringUtil.isNotEmpty(username)) {
			return username;
		}

		//token可能有特殊字符，前端encode了，这里需要decode一下
		String decodetoken = EncryptUtil.decodeURL(token);
		username = tokenToUsername(decodetoken);
		if (StringUtil.isNotEmpty(username)) {
			return username;
		}

		return null;
	}

	public static boolean havePermission(User user, String url) {
		if (user == null) {
			return false;
		}

		/*boolean isPermission = true;
			Long[] roleIds = user.getRoleIds();
			if (ArrayUtils.isEmpty(roleIds)) {
				commonRedisService.del(user.getUsername());
				return false;//用户权限信息为空
			}
			loadAllRoleResources();
			String result = Arrays.toString(roleIds).replaceAll(" +", "");
			String[] roleIdsStrArray = result.substring(1, result.length() - 1).split(",");

			List<String> resourceList = commonRedisService.hmget(CommonConstants.ROLE_RESOURCES_HASH_KEY, roleIdsStrArray);
			int index = url.indexOf("?");
			if (index > -1) {
				url = url.substring(0, index);
			}

			for (String resource : resourceList) {
				if (resource != null && resource.contains(url)) {
					isPermission = true;
					break;
				}
			}*/
		return true;
	}

	/**
	 * 获取登录用户的用户名
	 * @param request 请求对象
	 * @return 用户名
	 */
	public static User getLoginedUserByUsername(String username) {
		if (StringUtil.isEmpty(username)) {
			return null;
		}
		return userCacheService.getLoginUser(username);
	}

	/**
	 * token转化用户名
	 */
	public static String tokenToUsername(String token) {
		if (StringUtil.isEmpty(token)) {
			return null;
		}

		return EncryptUtil.decodeCookieValue(token, SecretKeyConstant.COOKIE_SECRET_KEY);
	}

	/**
	 * 用户名转化token
	 */
	public static String usernameToToken(String username) {
		if (StringUtil.isEmpty(username)) {
			return null;
		}

		return EncryptUtil.encodeCookieValue(username, SecretKeyConstant.COOKIE_SECRET_KEY);
	}

}
