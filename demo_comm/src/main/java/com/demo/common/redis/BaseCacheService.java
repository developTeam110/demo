package com.demo.common.redis;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.codehaus.jackson.type.TypeReference;

/** 
* @Description: redis结构缓存抽象基类,底层实现可以是redis service 或 second cache service
* @author huan.wang@baidao.com   
* @date 2012-12-7 上午10:55:33 
*/
public interface BaseCacheService {
	public List<String> hkeys(String pattern) throws TimeoutException;

	public List<String> hmget(final String key, final String... fields) throws TimeoutException;

	public Map<String, String> hgetAll(String key) throws TimeoutException;

	public Long hset(String key, String field, String value) throws TimeoutException;

	public Long expire(String key, int secondExpire) throws TimeoutException;

	public Long hdel(String key, String field) throws TimeoutException;

	public String hget(String key, String field) throws TimeoutException;

	public Long rpush(String key, String string) throws TimeoutException;

	public String lpop(String key) throws TimeoutException;

	public Long llen(String key) throws TimeoutException;

	public Long hlen(String key) throws TimeoutException;

	public Boolean exists(String key) throws TimeoutException;

	public boolean hexists(String key, String field) throws TimeoutException;

	public Long ttl(String key) throws TimeoutException;

	public void setAndExpire(String key, Object o, int expire) throws TimeoutException;

	public <T> T get(String key, Class<T> clazz) throws TimeoutException;

	public <T> T get(String key, TypeReference<T> clazz) throws TimeoutException;

	public Long incr(String key) throws TimeoutException;

	public void del(String key) throws TimeoutException;
}
