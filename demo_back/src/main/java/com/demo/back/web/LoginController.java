package com.demo.back.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.back.po.User;
import com.demo.back.service.UserCacheService;
import com.demo.back.service.UserService;
import com.demo.common.constant.ErrorCode;
import com.demo.common.helper.CookieHelper;
import com.demo.common.helper.UserHelper;
import com.demo.common.util.StringUtil;
import com.demo.common.vo.Result;

@Controller
@RequestMapping("admin")
public class LoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserCacheService userCacheService;

	@RequestMapping(value = "login", method = { RequestMethod.GET })
	public String doGetlogin(Model model) {
		return "/login";
	}

	@ResponseBody
	@RequestMapping(value = "login", method = { RequestMethod.POST })
	public Object doPostlogin(HttpServletRequest request, HttpServletResponse response, String loginName, String password, String verificationCode) {
		Result result = new Result();
		if (StringUtil.isEmpty(loginName)) {
			result.setErrorCode(ErrorCode.PARAM_LOGIN_NAME_NOT_EMPTY);
			return result;
		}

		if (StringUtil.isEmpty(password)) {
			result.setErrorCode(ErrorCode.PARAM_PASSWORD_NOT_EMPTY);
			return result;
		}

		User user = userService.getUserByloginNameAndPwd(loginName.trim(), password.trim());
		if (user == null) {
			result.setErrorCode(ErrorCode.PARAM_LOGIN_NAME_OR_PWD_ERROR);
			return result;
		}

		if (!user.getInnerFlag()) {
			result.setErrorCode(ErrorCode.USER_IS_NOT_INNER);
			return result;
		}

		if (User.STATUS.FREEZE.code().equals(user.getStatus())) {
			result.setErrorCode(ErrorCode.USER_IS_FREEZE);
			return result;
		}

		user.setLastLoginTime(new Date());
		user.setLastLoginIp(UserHelper.getIpAddr(request));
		userService.updateUserByUsername(user);

		CookieHelper.addLoginCookie(request, response, user);
		userCacheService.saveLoginUser(user);

		result.setErrorCode(ErrorCode.SUCCESS);
		return result;
	}

	@RequestMapping(value = "logout", method = { RequestMethod.GET, RequestMethod.POST })
	public String getTest(Model model, String username) {
		User user = userService.getUserFromCacheOrDbByUsername(username);
		System.out.println(user);
		return "/login";
	}

}
