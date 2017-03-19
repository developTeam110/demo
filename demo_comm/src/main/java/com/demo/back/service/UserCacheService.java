package com.demo.back.service;

import com.demo.back.po.User;

public interface UserCacheService {

	void saveUser(User user);

	void deleteUser(String username);

	User getUser(String username);

	void saveLoginUser(User user);

	void deleteLoginUser(String username);

	User getLoginUser(String username);
}
