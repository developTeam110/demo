package com.demo.back.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/permission")
public class PermissionController {

	@RequestMapping(value = "denied")
	public String index(Model model) {
		return "errorPages/withoutPermission";
	}

}
