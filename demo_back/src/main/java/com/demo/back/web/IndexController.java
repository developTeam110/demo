package com.demo.back.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.back.po.User;
import com.demo.back.service.UserService;
import com.demo.common.constant.ErrorCode;
import com.demo.common.util.JsonUtil;
import com.demo.common.util.StringUtil;
import com.demo.common.vo.Result;

@Controller
@RequestMapping("admin")
public class IndexController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "index")
	public String index(Model model) {
		return "/index";
	}

	@RequestMapping(value = "tologin", method = { RequestMethod.GET })
	public String doGetlogin() {
		return "/login";
	}

	@ResponseBody
	@RequestMapping(value = "login", method = { RequestMethod.POST })
	public Object doPostlogin(@RequestParam String loginName, @RequestParam String password, @RequestParam(required=false)String verificationCode) {
		Result result = new Result();
		if (StringUtil.isEmpty(loginName)) {
			result.setErrorCode(ErrorCode.PARAM_LOGIN_NAME_NOT_EMPTY);
			return result;
		}

		if (StringUtil.isEmpty(password)) {
			result.setErrorCode(ErrorCode.PARAM_PASSWORD_NOT_EMPTY);
			return result;
		}

		User user = userService.getUserByloginNameAndPwd(loginName, password);
		if (user == null) {
			result.setErrorCode(ErrorCode.PARAM_LOGIN_NAME_OR_PWD_ERROR);
			return result;
		}

		user.setPassword(null);
		result.setData(user);
		result.setErrorCode(ErrorCode.SUCCESS);
		return result;
	}

	@RequestMapping(value = "get", method = { RequestMethod.GET, RequestMethod.POST })
	public String getTest(Model model, String username, String password) {
		User user = userService.getUserFromCacheOrDbByUsername(username);
		System.out.println(user);
		return "/login";
	}

}
