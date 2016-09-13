package com.demo.back.dao;

import com.demo.back.po.User;

public interface UserMapper {

	int save(User user);

	User getByUsername(String username);
}
