package com.demo.back.web;

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
import com.demo.common.vo.Page;
import com.demo.common.vo.Result;

@Controller
@RequestMapping("admin/user")
public class UserController {

	@Autowired
	private UserService userService;
 
	@Autowired
	private UserCacheService userCacheService;

	@RequestMapping(value = "toList", method = { RequestMethod.GET })
	public Object toUserList(Model model) {
		model.addAttribute("statuses", User.STATUS.values());
		return "/user/userList";
	}

	@ResponseBody
	@RequestMapping(value = "list", method = { RequestMethod.GET })
	public Object getUserList(HttpServletRequest request, HttpServletResponse response, Page<User> paramPage, User paramUser) {
		Page<User> resultPage = userService.getPageByCondition(paramPage, paramUser);
		return resultPage;
	}

	@RequestMapping(value = "toAddUser", method = { RequestMethod.GET })
	public Object toAddUser(Model model) {
		return "/user/addUser";
	}

	@ResponseBody
	@RequestMapping(value = "addUser", method = { RequestMethod.POST })
	public Object addUser(Model model, User user) {
		Result result = new Result();
		return result;
	}
}
