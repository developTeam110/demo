package com.demo.back.service;

import com.demo.back.po.User;

public interface UserService {

	int saveUser(User user);

	int updateUserByUsername(User user);

	int updateUserSelectiveByUsername(User user);

	User getUserByUsername(String username);

	User getUserByloginNameAndPwd(String loginName, String pwd);

	User getUserFromCacheOrDbByUsername(String username);

}
