package com.demo.common.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.back.po.User;
import com.demo.common.helper.UserHelper;
import com.demo.common.listener.InitSystemListener;
import com.demo.common.util.FilterUtil;
import com.demo.common.util.PropertiesUtil;
import com.demo.common.util.StringUtil;

public class SecurityFilter implements Filter {
	private final static Logger logger = LoggerFactory.getLogger(InitSystemListener.class);

	private String trustUrlPatterns = null;

	private List<String> trustUrls;
	private ServletContext servletContext;

	private final String AJAX_ANT_PATTERN = "/ajax/**";

	private final String AJAX_PATTERN = "/ajax/";

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) req;
		HttpServletResponse httpResponse = (HttpServletResponse) res;

		final String url = FilterUtil.getRequestedUrl(req);

		boolean isNeedLogin = false;
		boolean havePermission = false;

		String username = UserHelper.getLoginedUsername(httpRequest);
		if (StringUtil.isEmpty(username)) {
			
		}

		User loginUser = null;
		// 判断用户所请求的URL 是否需要保护。
		boolean isProtected = this.checkIfIsProtectedUrl(url);

		//标识自动登录逻辑是否成功的触发过，成功触发一次则该次不执行单机登录限制检测
		boolean autoLogined = false;
		do {
			User user = this.getUserFromCache(username);

			if (!isProtected) {
				// 如果不需要保护，则直接往下。
				havePermission = true;
				loginUser = user;
				break;
			}

			// 如果需要保护
			// 1、判断用户是否登录
			if (username == null) {
				isNeedLogin = true;
				break;
			}

			//do auto login and check again
			if (user == null) {
				try {
					user = UserHelper.autoLogin((HttpServletRequest) req, (HttpServletResponse) res, username);
				} catch (Exception e) {
					logger.error("do auto login failed", e);
				}

				if (user == null) {
					isNeedLogin = true;
					break;
				}

				//这里表明用户自动登录逻辑触发成功
				autoLogined = true;
			}
			havePermission = this.ifUserHavePermission(user, url);
			loginUser = user;
		} while (false);

		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;

		//如果没有权限，则出提示或去登录页
		if (!havePermission) {
			if (isNeedLogin) {
				redirect2Login(req, res);
				return;
			}

			request.setAttribute("message", "您没有权限访问该资源，请确定您使用的账户[ " + username + " ]拥有访问权限O(∩_∩)O~");
			request.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(request, response);
			return;
		}

		if (loginUser == null || isSpecialLoginUrl(url)) {
			chain.doFilter(req, res);
			return;
		}

		//针对*.do等动态页面，作进一步的处理
		if (isActionUrl(url)) {
			loginUser.setResources(null);
			request.setAttribute("loginUser", loginUser);
		}

		chain.doFilter(req, res);
	}

	/**
	 * @Description: 这些特殊链接，不用走权限过滤
	 * @param url
	 * @return
	 * @author huan.wang@baidao.com
	 * @date 2012-12-17 下午04:39:58
	 */

	private boolean isSpecialLoginUrl(String url) {
		return url.contains("login.do") || url.contains("logout.do") || url.contains("forceLogin.do");
	}

	/**
	 * @Description: 验证是否是受权限保护的链接
	 * @param url
	 * @return
	 * @author huan.wang@baidao.com
	 * @date 2012-11-26 下午05:33:32
	 */
	private boolean checkIfIsProtectedUrl(String url) {
		// 验证是否是被信任的url。
		if (FilterUtil.checkRequestUrl(url, trustUrls)) {
			return false;
		}

		// 验证是否是Ajax
		if (this.isAjaxRequest(url)) {
			return false;
		}

		// 验证该URL是否需要保护
		final List<String> protectedResources = getAllResources();
		if (protectedResources != null) {
			return FilterUtil.checkRequestUrl(url, protectedResources);
		}

		return false;
	}

	private void redirect2Login(ServletRequest req, ServletResponse res) throws IOException {
		final HttpServletRequest request = (HttpServletRequest) req;
		final String qString = request.getQueryString();

		final String referer = request.getHeader("referer");
		final String serverName = request.getServerName();

		String returnUrl = request.getRequestURL().toString();

		if ("localhost".equals(serverName)) {
			if (StringUtil.isNotEmpty(referer)) {
				// 将localhost 替换成域名的形式。
				String replacement = referer.substring(referer.indexOf("//") + 2);
				replacement = replacement.substring(0, replacement.indexOf("/"));

				returnUrl = returnUrl.replaceFirst(serverName + ":" + request.getServerPort(), replacement);
			}
		}

		String loginAddress = loginUrl;
		do {
			if (returnUrl.contains("/y/")) {
				loginAddress = yuyinLoginUrl;
				break;
			}

			if (returnUrl.contains("/jry")) {
				loginAddress = jryLoginUrl;
				break;
			}

			if (returnUrl.contains("/ipad/")) {
				loginAddress = ipadLoginUrl;
				break;
			}
		} while (false);

		if (org.springframework.util.StringUtils.hasText(qString)) {
			returnUrl += "?" + qString;
		}

		returnUrl = URLEncoder.encode(returnUrl, "UTF-8");

		final HttpServletResponse response = (HttpServletResponse) res;
		response.sendRedirect(loginAddress + "?returnUrl=" + returnUrl);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		servletContext = config.getServletContext();

		// 获得初始化参数
		String param = config.getInitParameter("trustUrlPattern");
		if (StringUtil.isNotEmpty(param)) {
			trustUrlPatterns = param;
		}

		trustUrls = FilterUtil.spliteUrlPatterns(trustUrlPatterns);

		loginUrl = PropertiesUtil.getProperty("sso.login.url");
	}

	private boolean ifUserHavePermission(User user, String url) {
		return UserHelper.ifUserHavePermission(user, url);
	}

	private boolean isActionUrl(String url) {
		boolean isActionUrl = false;
		do {
			if (url.equalsIgnoreCase("/")) {
				isActionUrl = true;
				break;
			}

			final int index = url.indexOf(".");
			if (index <= 0) {
				isActionUrl = true;
				break;
			}

			// 判断是否符合*/*Ajax.*
			final String suffix = url.substring(index + 1);
			if (suffix.equalsIgnoreCase("do") || suffix.equalsIgnoreCase("jsp")) {
				isActionUrl = true;
				break;
			}
		} while (false);

		return isActionUrl;
	}

	private boolean isAjaxRequest(String url) {
		boolean isAjax = false;

		do {
			// 判断是否符合/ajax/* 这个pattern
			final List<String> ajaxPattern = new ArrayList<String>();
			ajaxPattern.add(AJAX_ANT_PATTERN);
			isAjax = FilterUtil.checkRequestUrl(url, ajaxPattern);
			if (isAjax) {
				break;
			}

			// 判断是否符合*/*Ajax.*
			if (url.indexOf(AJAX_PATTERN) > 0) {
				isAjax = true;
				break;
			}
		} while (false);

		return isAjax;
	}

	private List<String> getAllResources() {
		List<String> resources = null;
		try {
			if (servletContext != null) {
				final Object tmp = servletContext.getAttribute(CommonConstants.APPLICATION_RESOURCES);

				if (tmp != null) {
					resources = (List<String>) tmp;
				}
			}
		} catch (Exception e) {
			logger.error("Exception is  =======", e);
		}

		return resources;
	}
}
