package com.demo.admin.dao;

import org.springframework.stereotype.Repository;

import com.demo.back.po.User;
import com.demo.common.mybatis.MyBatisDao;

@Repository
public class UserDao extends MyBatisDao {

	public UserDao() {
		super("T_USER");
	}

	public int save(User user) {
		return super.insert("save", user);
	}

	public int updateByUsername(User user) {
		return super.update("updateByUsername", user);
	}

	public int updateSelectiveByUsername(User user) {
		return super.update("updateSelectiveByUsername", user);
	}

	public int getByUsername(String username) {
		return super.get("getByUsername", username);
	}

}