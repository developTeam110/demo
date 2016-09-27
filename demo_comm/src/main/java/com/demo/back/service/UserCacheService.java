package com.demo.back.service;

import com.demo.back.po.User;

public interface UserCacheService {

	void saveUser(User user);

	User getUser(String username);

	void saveLoginUser(User user);

	User getLoginUser(String username);
}
