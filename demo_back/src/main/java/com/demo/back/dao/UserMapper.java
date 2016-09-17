package com.demo.back.dao;

import org.apache.ibatis.annotations.Param;

import com.demo.back.po.User;

public interface UserMapper {

	int save(User user);

	int updateByUsername(User user);

	int updateSelectiveByUsername(User user);

	User getByUsername(String username);

	User getByloginNameAndPwd(@Param("loginName")String loginName, @Param("pwd")String pwd);

}
