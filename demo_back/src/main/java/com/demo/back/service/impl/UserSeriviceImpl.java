package com.demo.back.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.back.dao.UserMapper;
import com.demo.back.po.User;
import com.demo.back.service.UserCacheService;
import com.demo.back.service.UserService;
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
