package com.demo.back.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.back.dao.UserMapper;
import com.demo.back.po.User;
import com.demo.back.service.UserService;

@Service(value="userService")
public class UserSeriviceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;

	@Override
	public int saveUser(User user) {
		return userMapper.save(user);
	}

	@Override
	public User getUserByUsername(String username) {
		return userMapper.getByUsername(username);
	}

}
