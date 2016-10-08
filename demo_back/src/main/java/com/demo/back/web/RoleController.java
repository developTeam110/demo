package com.demo.back.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.back.po.Role;
import com.demo.back.service.RoleService;
import com.demo.common.constant.ErrorCode;
import com.demo.common.exception.BusinessException;
import com.demo.common.vo.Page;
import com.demo.common.vo.Result;

@Controller
@RequestMapping("admin/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "toList", method = { RequestMethod.GET, RequestMethod.POST })
	public String toRoleList(Model model) {
		return "/role/roleList";
	}

	@ResponseBody
	@RequestMapping(value = "list", method = { RequestMethod.GET, RequestMethod.POST })
	public Object getRoleList(HttpServletRequest request, HttpServletResponse response, Page<Role> paramPage, Role paramRole) {
		Page<Role> resultPage = roleService.getPageByCondition(paramPage, paramRole);
		return resultPage;
	}

	@RequestMapping(value = "toAdd", method = { RequestMethod.GET, RequestMethod.POST })
	public String toAddRole(Model model) {
		return "/role/addRole";
	}

	@ResponseBody
	@RequestMapping(value = "add", method = { RequestMethod.POST })
	public Object addRole(HttpServletRequest request, Role role) {
		Result result = new Result();

		try {
			roleService.saveRole(role);
			result.setErrorCode(ErrorCode.SUCCESS);
		} catch (BusinessException e) {
			result.setErrorCode(e.getErrorCode());
		}

		return result;
	}

	@RequestMapping(value = "toEdit", method = { RequestMethod.GET, RequestMethod.POST })
	public String toEditRole(Model model, Long uniqueId) {
		Role role = roleService.getRoleById(uniqueId);
		model.addAttribute("role", role);
		return "/role/editRole";
	}

	@ResponseBody
	@RequestMapping(value = "edit", method = { RequestMethod.POST })
	public Object editRole(HttpServletRequest request, Role role) {
		Result result = new Result();

		try {
			roleService.updateRoleById(role);
			result.setErrorCode(ErrorCode.SUCCESS);
		} catch (BusinessException e) {
			result.setErrorCode(e.getErrorCode());
		}

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "delete", method = {RequestMethod.GET, RequestMethod.POST })
	public Object deleteRoleList(Long[] uniqueIds) {
		Result result = new Result();
		if (uniqueIds == null) {
			result.setErrorCode(ErrorCode.PARAM_NO_SELECTED_ITEM);
			return result;
		}

		try {
			roleService.deleteByIds(uniqueIds);
			result.setErrorCode(ErrorCode.SUCCESS);
		} catch (BusinessException e) {
			result.setErrorCode(e.getErrorCode());
		}

		return result;
	}

}
