package com.demo.back.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.back.dao.UserMapper;
import com.demo.back.po.User;
import com.demo.back.service.UserCacheService;
import com.demo.back.service.UserService;
import com.demo.common.constant.ErrorCode;
import com.demo.common.exception.BusinessException;
import com.demo.common.helper.UserHelper;
import com.demo.common.util.StringUtil;
import com.demo.common.vo.Page;
import com.google.common.base.Preconditions;

@Service(value="userService")
public class UserSeriviceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserCacheService userCacheService;

	@Override
	public int saveUser(User user) {
		Preconditions.checkArgument(user != null, "user is null.");

		/*密码校验*/
		String password = user.getPassword();
		if (StringUtil.isEmpty(password)) {
			throw new BusinessException(ErrorCode.PARAM_PASSWORD_NOT_EMPTY);
		}

		if (!StringUtil.isValidPassword(password)) {
			throw new BusinessException(ErrorCode.PARAM_PASSWORD_INVALID);
		}

		/*昵称校验*/
		String nickname = user.getNickname();
		if (StringUtil.isEmpty(nickname)) {
			throw new BusinessException(ErrorCode.PARAM_NICKNAME_NOT_EMPTY);
		}

		/*登录名校验*/
		String loginString = user.getLoginString();
		if (StringUtil.isNotEmpty(loginString)) {
			if (!StringUtil.isValidLoginName(loginString)) {
				throw new BusinessException(ErrorCode.PARAM_LOGIN_NAME_INVALID);
			}

			User paramUser = new User();
			paramUser.setLoginString(loginString);
			int count = userMapper.getCountByCondition(null, paramUser);
			if (count > 0) {
				throw new BusinessException(ErrorCode.PARAM_LOGIN_NAME_IS_EXISTED);
			}
		}

		/*邮箱校验*/
		String email = user.getEmail();
		if (StringUtil.isNotEmpty(email)) {
			if (!StringUtil.isValidEmail(loginString)) {
				throw new BusinessException(ErrorCode.PARAM_EMAIL_INVALID);
			}

			User paramUser = new User();
			paramUser.setEmail(email);
			int count = userMapper.getCountByCondition(null, paramUser);
			if (count > 0) {
				throw new BusinessException(ErrorCode.PARAM_EMAIL_IS_EXISTED);
			}
		}

		/*电话号校验*/
		String phone = user.getPhone();
		if (StringUtil.isNotEmpty(phone)) {
			if (!StringUtil.isValidPhone(phone)) {
				throw new BusinessException(ErrorCode.PARAM_EMAIL_INVALID);
			}

			User paramUser = new User();
			paramUser.setPhone(phone);
			int count = userMapper.getCountByCondition(null, paramUser);
			if (count > 0) {
				throw new BusinessException(ErrorCode.PARAM_PHONE_IS_EXISTED);
			}
		}

		user.setUsername(StringUtil.generateRandomUsernameByUUID());
		user.setInnerFlag(user.getInnerFlag() == null ? false : user.getInnerFlag());
		Date currentDate = new Date();
		user.setCreateTime(currentDate);
		user.setUpdateTime(currentDate);
		return userMapper.save(user);
	}

	@Override
	public int updateUserByUsername(User user) {
		Preconditions.checkArgument(user != null, "user is null.");
		Preconditions.checkArgument(StringUtil.isNotEmpty(user.getUsername()), "username is empty.");

		return userMapper.updateByUsername(user);
	}

	@Override
	public int updateUserSelectiveByUsername(User user) {
		Preconditions.checkArgument(user != null, "user is null.");
		Preconditions.checkArgument(StringUtil.isNotEmpty(user.getUsername()), "username is empty.");

		user.setUpdateTime(new Date());
		return userMapper.updateSelectiveByUsername(user);
	}

	@Override
	public User getUserByUsername(String username) {
		return userMapper.getByUsername(username);
	}

	@Override
	public User getUserByloginNameAndPwd(String loginName, String pwd) {
		return userMapper.getByloginNameAndPwd(loginName, pwd);
	}

	@Override
	public User getUserFromCacheOrDbByUsername(String username) {
		Preconditions.checkArgument(StringUtil.isNotEmpty(username), "username is empty.");

		User user = userCacheService.getUser(username);
		if (user != null) {
			return user;
		}

		user = userMapper.getByUsername(username);
		if (user != null) {
			userCacheService.saveUser(user);
		}
		return user;
	}

	@Override
	public Page<User> getPageByCondition(Page<User> page, User user) {
		int count = userMapper.getCountByCondition(page, user);
		page.setTotal(count);
		if (count != 0) {
			List<User> userList = userMapper.getListByCondition(page, user);
			page.setRows(userList);
		}
		return page;
	}

}
