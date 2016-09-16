package com.demo.common.constant;

import com.demo.common.constant.CommEnum.TIME_SECONDS;


/** 
* REVIEW
* @Description: 缓存KEY公共枚举
* @author jingkun.wang@baidao.com
* @date 2016年7月2日 下午3:03:41
* 
*/
public enum CacheEnum {

	/**
	 * 活跃用户缓存（结构：MAP，failed：username）
	 */
	USER_MAP_KEY_USERNAME("user:map:key:username:", TIME_SECONDS.ONE_DAY.sec());

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
