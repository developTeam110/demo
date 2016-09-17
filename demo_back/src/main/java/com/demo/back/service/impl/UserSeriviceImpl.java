package com.demo.back.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.back.dao.UserMapper;
import com.demo.back.po.User;
import com.demo.back.service.UserCacheService;
import com.demo.back.service.UserService;
import com.demo.common.util.StringUtil;
import com.google.common.base.Preconditions;

@Service(value="userService")
public class UserSeriviceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserCacheService userCacheService;

	@Override
	public int saveUser(User user) {
		return userMapper.save(user);
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

}
