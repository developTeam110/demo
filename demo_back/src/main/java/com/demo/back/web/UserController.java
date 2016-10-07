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
import com.demo.common.constant.SecretKeyConstant;
import com.demo.common.exception.BusinessException;
import com.demo.common.helper.UserHelper;
import com.demo.common.util.EncryptUtil;
import com.demo.common.vo.Page;
import com.demo.common.vo.Result;

@Controller
@RequestMapping("admin/user")
public class UserController {

	@Autowired
	private UserService userService;
 
	@Autowired
	private UserCacheService userCacheService;

	@RequestMapping(value = "toList", method = { RequestMethod.GET, RequestMethod.POST })
	public String toUserList(Model model) {
		model.addAttribute("statuses", User.STATUS.values());
		return "/user/userList";
	}

	@ResponseBody
	@RequestMapping(value = "list", method = { RequestMethod.GET, RequestMethod.POST })
	public Object getUserList(HttpServletRequest request, HttpServletResponse response, Page<User> paramPage, User paramUser) {
		Page<User> resultPage = userService.getPageByCondition(paramPage, paramUser);
		return resultPage;
	}

	@RequestMapping(value = "toAdd", method = { RequestMethod.GET, RequestMethod.POST })
	public String toAddUser(Model model) {
		model.addAttribute("statuses", User.STATUS.values());
		return "/user/addUser";
	}

	@ResponseBody
	@RequestMapping(value = "add", method = { RequestMethod.POST })
	public Object addUser(HttpServletRequest request, User user) {
		Result result = new Result();

		try {
			userService.checkUserParam(user);//校验参数合法性

			String md5Password = EncryptUtil.encodeByMd5(user.getPassword(), SecretKeyConstant.PASSWORD_SECRET_KEY);
			user.setPassword(md5Password);

			user.setLastLoginIp(UserHelper.getIpAddr(request));
			user.setLastLoginTime(new Date());
			userService.saveUser(user);
			result.setErrorCode(ErrorCode.SUCCESS);
		} catch (BusinessException e) {
			result.setErrorCode(e.getErrorCode());
		}

		return result;
	}

	@RequestMapping(value = "toEdit", method = { RequestMethod.GET, RequestMethod.POST })
	public String toEditUser(Model model, String uniqueId) {
		User user = userService.getUserByUsername(uniqueId);
		model.addAttribute("user", user);
		model.addAttribute("statuses", User.STATUS.values());
		return "/user/editUser";
	}

	@ResponseBody
	@RequestMapping(value = "edit", method = { RequestMethod.POST })
	public Object editUser(HttpServletRequest request, User user) {
		Result result = new Result();

		try {
			userService.checkUserParam(user);//校验参数合法性

			String md5Password = EncryptUtil.encodeByMd5(user.getPassword(), SecretKeyConstant.PASSWORD_SECRET_KEY);
			user.setPassword(md5Password);
			userService.updateUserByUsername(user);
			result.setErrorCode(ErrorCode.SUCCESS);
		} catch (BusinessException e) {
			result.setErrorCode(e.getErrorCode());
		}

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "delete", method = {RequestMethod.GET, RequestMethod.POST })
	public Object deleteUserList(String[] uniqueIds) {
		Result result = new Result();
		if (uniqueIds == null) {
			result.setErrorCode(ErrorCode.PARAM_NO_SELECTED_ITEM);
			return result;
		}

		try {
			for (String username : uniqueIds) {
				User paramUser = new User();
				paramUser.setUsername(username);
				paramUser.setStatus(User.STATUS.DELETE.code());
				userService.updateUserSelectiveByUsername(paramUser);
			}
			result.setErrorCode(ErrorCode.SUCCESS);
		} catch (BusinessException e) {
			result.setErrorCode(e.getErrorCode());
		}

		return result;
	}

}
