package com.demo.back.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.back.po.User;
import com.demo.back.service.UserService;

@Controller
@RequestMapping("admin")
public class IndexController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "index")
	public String index(Model model) {
		return "/index";
	}

	@RequestMapping(value = "login", method = { RequestMethod.GET })
	public String doGetlogin(Model model) {
		model.addAttribute("path", "222");
		return "/login";
	}

	@RequestMapping(value = "login", method = { RequestMethod.POST })
	public String doPostlogin(Model model, String username, String password) {
		return "/login";
	}

	@RequestMapping(value = "get", method = { RequestMethod.GET, RequestMethod.POST })
	public String getTest(Model model, String username, String password) {
		User user = userService.getUserByUsername(username);
		System.out.println(user);
		return "/login";
	}
}
