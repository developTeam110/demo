package com.demo.common.constant;

import com.demo.common.constant.CommEnum.TIME_SECONDS;

public enum CacheEnum {

	/* 用户相关缓存 KEY */
	/**
	 * 活跃用户缓存（结构：MAP，failed：username）
	 */
	USER_MAP_KEY_USERNAME("user:map:key:username:", TIME_SECONDS.ONE_DAY.sec()),

	/**
	 * 登录用户缓存（结构：MAP，failed：username）
	 */
	USER_LOGIN_MAP_KEY_USERNAME(null, TIME_SECONDS.ONE_DAY.sec());

	private String key;
	private Integer seconds;

	CacheEnum(String key, Integer seconds) {
		this.key = key;
		this.seconds = seconds;
	}

	public String key() {
		return this.key;
	}

	public Integer seconds() {
		return this.seconds;
	}

	@Override
	public String toString() {
		return this.name();
	}

}
