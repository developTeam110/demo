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
			//TODO 跳转登录页
			this.redirectToLogin(httpRequest, httpResponse);
		}

		User loginUser = UserHelper.getLoginedUserByUsername(username);
		if (loginUser == null) {
			//TODO 跳转过期登录页
		}


		//TODO 校验用户是否有访问该资源权限
		if (!UserHelper.havePermission(loginUser, url)) {
			httpRequest.setAttribute("message", "您没有权限访问该资源，请确定您使用的账户[ " + username + " ]拥有访问权限O(∩_∩)O~");
			httpRequest.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(httpRequest, httpResponse);
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

		return false;
	}

	/**
	 * 跳转至登录
	 */
	private void redirectToLogin(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
		final String queryString = httpRequest.getQueryString();
		final String referer = httpRequest.getHeader("referer");
		final String serverName = httpRequest.getServerName();
		String returnUrl = httpRequest.getRequestURL().toString();

		String loginUrl = "/demo_back/admin/login.do";
		if (StringUtil.isNotEmpty(queryString)) {
			returnUrl += "?" + queryString;
		}

		returnUrl = URLEncoder.encode(returnUrl, "UTF-8");
		httpResponse.sendRedirect(loginUrl + "?returnUrl=" + returnUrl);
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
