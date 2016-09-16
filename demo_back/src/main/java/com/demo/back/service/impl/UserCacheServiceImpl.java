package com.demo.back.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.back.po.User;
import com.demo.back.service.UserCacheService;
import com.demo.common.constant.CacheEnum;
import com.demo.common.redis.RedisService;
import com.demo.common.util.JsonUtil;

@Service(value="userCacheService")
public class UserCacheServiceImpl implements UserCacheService{

	@Autowired
	private RedisService redisService;

	@Override
	public void saveUser(User user) {
		redisService.hset(CacheEnum.USER_MAP_KEY_USERNAME.key(), user.getUsername(), JsonUtil.Object2Json(user));
		redisService.expire(CacheEnum.USER_MAP_KEY_USERNAME.key(), CacheEnum.USER_MAP_KEY_USERNAME.seconds());
	}

	@Override
	public User getUser(String username) {
		return redisService.get(username, User.class);
	}

}
