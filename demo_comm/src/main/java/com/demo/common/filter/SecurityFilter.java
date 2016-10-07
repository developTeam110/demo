package com.demo.common.filter;

import java.io.IOException;
import java.net.URLEncoder;
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

import com.demo.back.po.User;
import com.demo.common.helper.UserHelper;
import com.demo.common.util.FilterUtil;
import com.demo.common.util.StringUtil;

public class SecurityFilter implements Filter {
	private static final String LOGIN_URL = "/admin/login.do";
	private static final String RELOGIN_URL = "/admin/relogin.do";
	private String trustUrlPatterns = null;
	private List<String> trustUrls;
	private ServletContext servletContext;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) req;
		HttpServletResponse httpResponse = (HttpServletResponse) res;

		final String url = FilterUtil.getRequestedUrl(req);

		//校验该资源是否是受检资源
		if (!this.isProtectedUrl(url)) {
			chain.doFilter(req, res);
			return;
		}

		String username = UserHelper.getLoginedUsername(httpRequest);
		if (StringUtil.isEmpty(username)) {
			this.redirectToLogin(httpRequest, httpResponse);
			return;
		}

		User loginUser = UserHelper.getLoginedUserByUsername(username);
		//跳转过期登录页
		if (loginUser == null) {
			this.redirectToReLogin(httpRequest, httpResponse);
			return;
		}

		//校验用户是否有访问该资源权限
		if (!UserHelper.havePermission(loginUser, url)) {
			httpRequest.getRequestDispatcher("/admin/permission/denied.do").forward(httpRequest, httpResponse);
			return;
		}

		chain.doFilter(req, res);
	}

	/**
	 * @Description: 这些特殊资源，不用走权限过滤
	 */
	private boolean isSpecialLoginUrl(String url) {
		return url.contains("login.do") || url.contains("logout.do") || url.contains("overdueLogin.do");
	}

	/**
	 * 验证是否是受权限保护的资源
	 */
	private boolean isProtectedUrl(String url) {
		//是否是特殊资源
		if (this.isSpecialLoginUrl(url)) {
			return false;
		}

		//TODO 是否是不受保护资源

		return true;
	}

	/**
	 * 跳转至登录
	 */
	private void redirectToLogin(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
		final String queryString = httpRequest.getQueryString();
		final String referer = httpRequest.getHeader("referer");
		final String serverName = httpRequest.getServerName();
		final String contextPath = httpRequest.getContextPath();
		String returnUrl = httpRequest.getRequestURL().toString();

		if (StringUtil.isNotEmpty(queryString)) {
			returnUrl += "?" + queryString;
		}

		returnUrl = URLEncoder.encode(returnUrl, "UTF-8");
		httpResponse.sendRedirect(contextPath + LOGIN_URL + "?backUrl=" + returnUrl);
	}

	/**
	 * 跳转至过期登录
	 */
	private void redirectToReLogin(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
		final String queryString = httpRequest.getQueryString();
		final String contextPath = httpRequest.getContextPath();
		String returnUrl = httpRequest.getRequestURL().toString();

		if (StringUtil.isNotEmpty(queryString)) {
			returnUrl += "?" + queryString;
		}

		returnUrl = URLEncoder.encode(returnUrl, "UTF-8");
		httpResponse.sendRedirect(contextPath + RELOGIN_URL + "?backUrl=" + returnUrl);
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
	}

	@Override
	public void destroy() {
	}
}
