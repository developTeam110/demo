package com.demo.back.service;

import com.demo.back.po.User;
import com.demo.common.vo.Page;

public interface UserService {

	void checkUserParam(User user);

	int saveUser(User user);

	int updateUserByUsername(User user);

	int updateUserSelectiveByUsername(User user);

	User getUserByUsername(String username);

	User getUserByloginNameAndPwd(String loginName, String pwd);

	User getUserFromCacheOrDbByUsername(String username);

	Page<User> getPageByCondition(Page<User> page, User user);
}
