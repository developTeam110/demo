package com.demo.back.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.demo.back.po.User;
import com.demo.common.vo.Page;

public interface UserMapper {

	int save(User user);

	int updateByUsername(User user);

	int updateSelectiveByUsername(User user);

	User getByUsername(String username);

	User getByloginNameAndPwd(@Param("loginName")String loginName, @Param("pwd")String pwd);

	List<User> getListByCondition(@Param("page") Page<User> page, @Param("user") User user);

	int getCountByCondition(@Param("page") Page<User> page, @Param("user") User user);
}
