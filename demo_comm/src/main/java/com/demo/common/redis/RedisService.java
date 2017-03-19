package com.demo.common.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.util.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Transaction;

import com.demo.common.exception.RedisException;
import com.demo.common.util.JsonUtil;

public class RedisService implements BaseCacheService {
	private static final Log LOG = LogFactory.getLog(RedisService.class);

	private JedisPool jedisPool;
	public static final String NOT_FOUND = "nil";

	/*************************
		缓存池操作
	 *************************/
	/**
	 * 设置缓存池
	 * @Title: setJedisPool
	 * @Description:
	 * @param jedisPool
	 */
	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	/**
	 * 获取缓存池
	 * @Title: getJedis
	 * @Description:
	 * @return
	 * @throws RedisException
	 */
	public Jedis getJedis() throws RedisException {
		Jedis jedis = null;
		jedis = jedisPool.getResource();
		if (jedis == null) {
			LOG.warn("!!!!!no jedis resource can be fetched!!!!!!");
		}
		return jedis;
	}

	/**
	 * 释放缓存池
	 * @Title: releaseJedisInstance
	 * @Description:
	 * @param jedis
	 */
	public void releaseJedisInstance(Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}

	/*************************
	 	 对Key(键)操作命令集合
	     DEL
	     KEYS
	     RANDOMKEY
	     TTL
	     PTTL
	     EXISTS
	     MOVE
	     RENAME
	     RENAMENX
	     TYPE
	     EXPIRE
	     PEXPIRE
	     EXPIREAT
	     PEXPIREAT
	     PERSIST
	     SORT
	     OBJECT
	     MIGRATE
	     DUMP
	     RESTORE
	*************************/

	/**
	 * Remove the specified keys. If a given key does not exist no operation is
	 * performed for this key. The command returns the number of keys removed.
	 * Time complexity: O(1)
	 * @param keys
	 * @return Integer reply, specifically: an integer greater than 0 if one or
	 * more keys were removed 0 if none of the specified key existed
	 */
	public Long del(String... keys) throws RedisException {
		Long result = 0L;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			result = jedis.del(keys);
		} finally {
			releaseJedisInstance(jedis);
		}
		return result;
	}

	public void del(String key) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.del(key);
		} finally {
			releaseJedisInstance(jedis);
		}
	}

	/**
	 * 得到指导key的值列表。此方法返回一个指定类型的数据列表。
	 * @param key Key
	 * @param clazz TypeReference， 指导返回的类型。
	 * @return
	 * @throws RedisException
	 */
	public <T> T get(String key, TypeReference<T> clazz) throws RedisException {
		String json = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			json = jedis.get(key);
		} catch (RedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				releaseJedisInstance(jedis);
			}
		}
		if (json == null) {
			return null;
		} else {
			return JsonUtil.Json2Object(json, clazz);
		}
	}

	/**
	 * Test if the specified key exists. The command returns "0" if the key
	 * exists, otherwise "1" is returned. Note that even keys set with an empty
	 * string as value will return "1".
	 *
	 * Time complexity: O(1)
	 *
	 * @param key
	 * @return Boolean reply
	 */
	public Boolean exists(String key) throws RedisException {
		Boolean result = Boolean.FALSE;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			result = jedis.exists(key);
		} finally {
			releaseJedisInstance(jedis);
		}
		return result;
	}

	/**
	 * Returns all the keys matching the glob-style pattern as space separated
	 * strings. For example if you have in the database the keys "foo" and
	 * "foobar" the command "KEYS foo*" will return "foo foobar".
	 * <p>
	 * Note that while the time complexity for this operation is O(n) the
	 * constant times are pretty low. For example Redis running on an entry
	 * level laptop can scan a 1 million keys database in 40 milliseconds.
	 * <b>Still it's better to consider this one of the slow commands that may
	 * ruin the DB performance if not used with care.</b>
	 * <p>
	 * In other words this command is intended only for debugging and special
	 * operations like creating a script to change the DB schema. Don't use it
	 * in your normal code. Use Redis Sets in order to group together a subset
	 * of objects.
	 * <p>
	 * Glob style patterns examples:
	 * <ul>
	 * <li>h?llo will match hello hallo hhllo
	 * <li>h*llo will match hllo heeeello
	 * <li>h[ae]llo will match hello and hallo, but not hillo
	 * </ul>
	 * <p>
	 * Use \ to escape special chars if you want to match them verbatim.
	 * <p>
	 * Time complexity: O(n) (with n being the number of keys in the DB, and
	 * assuming keys and pattern of limited length)
	 *
	 * @param pattern
	 * @return Multi bulk reply
	 */
	public List<String> keys(String pattern) throws RedisException {
		List<String> result = new ArrayList<String>();
		Set<String> set = new HashSet<String>();
		Jedis jedis = null;
		try {
			jedis = getJedis();
			set = jedis.keys(pattern);
			if (set != null && !set.isEmpty()) {
				final Iterator<String> ite = set.iterator();
				while (ite.hasNext()) {
					result.add(ite.next());
				}
			}
		} finally {
			releaseJedisInstance(jedis);
		}
		return result;
	}

	/**
	 * Set a timeout on the specified key. After the timeout the key will be
	 * automatically deleted by the server. A key with an associated timeout is
	 * said to be volatile in Redis terminology.
	 * <p>
	 * Voltile keys are stored on disk like the other keys, the timeout is
	 * persistent too like all the other aspects of the dataset. Saving a
	 * dataset containing expires and stopping the server does not stop the flow
	 * of time as Redis stores on disk the time when the key will no longer be
	 * available as Unix time, and not the remaining seconds.
	 * <p>
	 * Since Redis 2.1.3 you can update the value of the timeout of a key
	 * already having an expire set. It is also possible to undo the expire at
	 * all turning the key into a normal key using the {@link #persist(String)
	 * PERSIST} command.
	 * <p>
	 * Time complexity: O(1)
	 *
	 * @see <ahref="http://code.google.com/p/redis/wiki/ExpireCommand">ExpireCommand</a>
	 *
	 * @param key
	 * @param seconds
	 * @return Integer reply, specifically: 1: the timeout was set. 0: the
	 *         timeout was not set since the key already has an associated
	 *         timeout (this may happen only in Redis versions < 2.1.3, Redis >=
	 *         2.1.3 will happily update the timeout), or the key does not
	 *         exist.
	 */
	public Long expire(String key, int seconds) throws RedisException {
		Long result = 0L;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			result = jedis.expire(key, seconds);
		} finally {
			releaseJedisInstance(jedis);
		}
		return result;
	}

	/**
	 * 以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)
	 * @Title: ttl
	 * @Description:
	 * @param key
	 * @return
	 * @throws RedisException
	 */
	public Long ttl(String key) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.ttl(key);
		} catch (RedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				releaseJedisInstance(jedis);
			}
		}
	}

	/**
	 * 混合命令    set:将字符串值 value 关联到 key 。如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
	 *			expire：为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
	 * @Title: setAndExpire
	 * @Description:
	 * @param key
	 * @param o
	 * @param expire
	 * @throws RedisException
	 */
	public void setAndExpire(String key, Object o, int expire) throws RedisException {
		String s = null;
		if (o == null)
			o = "";

		if (o instanceof String) {
			s = o.toString();
		} else if (o instanceof Integer) {
			s = o.toString();
		} else if (o instanceof Long) {
			s = o.toString();
		} else {
			s = JsonUtil.Object2Json(o);
		}
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.setex(key, expire, s);
		} catch (RedisException e) {
			throw e;
		} finally {
			releaseJedisInstance(jedis);
		}
	}

	/*************************
	 		 对String(字符串)操作命令集合
		     SET
		     SETNX
		     SETEX
		     PSETEX
		     SETRANGE
		     MSET
		     MSETNX
		     APPEND
		     GET
		     MGET
		     GETRANGE
		     GETSET
		     STRLEN
		     DECR
		     DECRBY
		     INCR
		     INCRBY
		     INCRBYFLOAT
		     SETBIT
		     GETBIT
		     BITOP
		     BITCOUNT
	*************************/

	/**
	 * Get the value of the specified key. If the key does not exist the special
	 * value 'nil' is returned. If the value stored at key is not a string an
	 * error is returned because GET can only handle string values.
	 * <p>
	 * Time complexity: O(1)
	 *
	 * @param key
	 * @return Bulk reply
	 */
	public String set(String key, String value) throws RedisException {
		String result = "";
		Jedis jedis = null;
		try {
			jedis = getJedis();
			result = jedis.set(key, value);
		} finally {
			releaseJedisInstance(jedis);
		}
		return result;
	}

	/**
	 * 将字符串值 value 关联到 key 。如果 key 已经持有其他值， SET 就覆写旧值，无视类型。
	 * @Title: set
	 * @Description:
	 * @param key
	 * @param o
	 * @throws RedisException
	 */
	public void set(String key, Object o) throws RedisException {
		String s = JsonUtil.Object2Json(o);
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.set(key, s);
		} catch (RedisException e) {
			throw e;
		} finally {
			releaseJedisInstance(jedis);
		}
	}

	/**
	 * 返回 key 所关联的字符串值。如果 key 不存在那么返回特殊值 nil 。假如 key 储存的值不是字符串类型，返回一个错误，因为 GET 只能用于处理字符串值。
	 * @Title: get
	 * @Description:
	 * @param <T>
	 * @param key
	 * @param clazz
	 * @return
	 * @throws RedisException
	 */
	public <T> T get(String key, Class<T> clazz) throws RedisException {
		String json = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			json = jedis.get(key);
		} catch (RedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				releaseJedisInstance(jedis);
			}
		}
		if (json == null) {
			return null;
		} else {
			return JsonUtil.Json2Object(json, clazz);
		}
	}

	/**
	 * Get the value of the specified key. If the key does not exist the special
	 * value 'nil' is returned. If the value stored at key is not a string an
	 * error is returned because GET can only handle string values.
	 * <p>
	 * Time complexity: O(1)
	 *
	 * @param key
	 * @return if not find, null id returned.
	 */
	public String get(String key) throws RedisException {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			String str = jedis.get(key);
			if (!NOT_FOUND.equals(str)) {
				result = str;
			}
		} finally {
			releaseJedisInstance(jedis);
		}
		return result;
	}

	/**
	 * 将 key 中储存的数字值减一。
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECR 操作。
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
	 * 本操作的值限制在 64 位(bit)有符号数字表示之内。
	 * @Title: decr
	 * @Description:
	 * @param key
	 * @return
	 * @throws RedisException
	 */
	public Long decr(String key) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.decr(key);
		} catch (RedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				releaseJedisInstance(jedis);
			}
		}
	}

	/**
	 * 将 key 中储存的数字值增一。
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
	 * 本操作的值限制在 64 位(bit)有符号数字表示之内。
	 * @Title: incr
	 * @Description:
	 * @param key
	 * @return
	 * @throws RedisException
	 */
	public Long incr(String key) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.incr(key);
		} catch (RedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				releaseJedisInstance(jedis);
			}
		}
	}

	/**
	 * 将 key 所储存的值加上增量 increment 。
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
	 * 本操作的值限制在 64 位(bit)有符号数字表示之内。
	 * @Title: incrBy
	 * @Description:
	 * @param key
	 * @param integer
	 * @return
	 * @throws RedisException
	 */
	public Long incrBy(String key, long integer) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.incrBy(key, integer);
		} catch (RedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				releaseJedisInstance(jedis);
			}
		}
	}

	/*************************
	 		 对Hash(哈希表)操作命令集合【key feild生成的hash值】
		     HSET
		     HSETNX
		     HMSET
		     HGET
		     HMGET
		     HGETALL
		     HDEL
		     HLEN
		     HEXISTS
		     HINCRBY
		     HINCRBYFLOAT
		     HKEYS
		     HVALS
	*************************/

	/**
	 * Return the number of items in a hash.
	 * <p>
	 * <b>Time complexity:</b> O(1)
	 *
	 * @param key
	 * @return The number of entries (fields) contained in the hash stored at
	 *         key. If the specified key does not exist, 0 is returned assuming
	 *         an empty hash.
	 */
	public Long hlen(String key) throws RedisException {
		Long len = 0L;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			len = jedis.hlen(key);
		} finally {
			releaseJedisInstance(jedis);
		}
		return len;
	}

	/**
	 *
	 * Set the specified hash field to the specified value.
	 * <p>
	 * If key does not exist, a new key holding a hash is created.
	 * <p>
	 * <b>Time complexity:</b> O(1)
	 *
	 * @param key
	 * @param field
	 * @param value
	 * @return If the field already exists, and the HSET just produced an update
	 *         of the value, 0 is returned, otherwise if a new field is created
	 *         1 is returned.
	 */
	public Long hset(String key, String field, String value) throws RedisException {
		Long result = 0L;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			result = jedis.hset(key, field, value);
		} finally {
			releaseJedisInstance(jedis);
		}
		return result;
	}

	/**
	 * If key holds a hash, retrieve the value associated to the specified
	 * field.
	 * <p>
	 * If the field is not found or the key does not exist, a special 'nil'
	 * value is returned.
	 * <p>
	 * <b>Time complexity:</b> O(1)
	 *
	 * @param key
	 * @param field
	 * @return if not find, null is returned.
	 */
	public String hget(String key, String field) throws RedisException {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			String str = jedis.hget(key, field);
			if (!NOT_FOUND.equals(str)) {
				result = str;
			}
		} finally {
			releaseJedisInstance(jedis);
		}
		return result;
	}

	/**
	 * Set the respective fields to the respective values. HMSET replaces old
	 * values with new values.
	 * <p>
	 * If key does not exist, a new key holding a hash is created.
	 * <p>
	 * <b>Time complexity:</b> O(N) (with N being the number of fields)
	 *
	 * @param key
	 * @param hash
	 * @return Always OK because HMSET can't fail
	 */
	public String hmset(String key, Map<String, String> hash) throws RedisException {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			result = jedis.hmset(key, hash);
		} finally {
			releaseJedisInstance(jedis);
		}
		return result;
	}

	/**
	 * Retrieve the values associated to the specified fields.
	 * <p>
	 * If some of the specified fields do not exist, nil values are returned.
	 * Non existing keys are considered like empty hashes.
	 * <p>
	 * <b>Time complexity:</b> O(N) (with N being the number of fields)
	 *
	 * @param key
	 * @param fields
	 * @return Multi Bulk Reply specifically a list of all the values associated
	 *         with the specified fields, in the same order of the request.
	 *         If fields is not exists, will return null.
	 * @throws RedisException
	 */
	@Override
	public List<String> hmget(final String key, final String... fields) throws RedisException {
		List<String> result = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			result = jedis.hmget(key, fields);
			if (result != null && !result.isEmpty()) {
				result.remove(NOT_FOUND);
			}
		} finally {
			releaseJedisInstance(jedis);
		}
		return result;
	}

	/**
	 * Return all the fields and associated values in a hash.
	 * <p>
	 * <b>Time complexity:</b> O(N), where N is the total number of entries
	 *
	 * @param key
	 * @return All the fields and values contained into a hash.
	 */
	public Map<String, String> hgetAll(String key) throws RedisException {
		Map<String, String> result = new HashMap<String, String>();
		Jedis jedis = null;
		try {
			jedis = getJedis();
			result = jedis.hgetAll(key);
		} finally {
			releaseJedisInstance(jedis);
		}
		return result;
	}

	/**
	 * Remove the specified field from an hash stored at key.
	 * <p>
	 * <b>Time complexity:</b> O(1)
	 *
	 * @param key
	 * @param field
	 * @return If the field was present in the hash it is deleted and 1 is
	 *         returned, otherwise 0 is returned and no operation is performed.
	 */
	public Long hdel(String key, String field) throws RedisException {
		Long result = 0L;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			result = jedis.hdel(key, field);
		} finally {
			releaseJedisInstance(jedis);
		}
		return result;
	}

	/**
	 * 返回哈希表 key 中的所有域。
	 *
	 * @param pattern
	 * @return
	 * @throws RedisException
	 * @see com.ifa.common.resource.BaseCacheService#hkeys(java.lang.String)
	 */
	@Override
	public List<String> hkeys(String pattern) throws RedisException {
		List<String> result = new ArrayList<String>();
		Set<String> set = new HashSet<String>();
		Jedis jedis = null;
		try {
			jedis = getJedis();
			set = jedis.hkeys(pattern);
			if (set != null && !set.isEmpty()) {
				final Iterator<String> ite = set.iterator();
				while (ite.hasNext()) {
					result.add(ite.next());
				}
			}
		} finally {
			releaseJedisInstance(jedis);
		}
		return result;
	}

	/**
	 * 为哈希表 key 中的域 field 的值加上增量 increment 。
	 * 增量也可以为负数，相当于对给定域进行减法操作。
	 * 如果 key 不存在，一个新的哈希表被创建并执行 HINCRBY 命令。
	 * 如果域 field 不存在，那么在执行命令前，域的值被初始化为 0 。
	 * 对一个储存字符串值的域 field 执行 HINCRBY 命令将造成一个错误。
	 * 本操作的值被限制在 64 位(bit)有符号数字表示之内。
	 * @Title: hincrBy
	 * @Description:
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 * @throws RedisException
	 */
	public Long hincrBy(String key, String field, long value) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.hincrBy(key, field, value);
		} catch (RedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				releaseJedisInstance(jedis);
			}
		}
	}

	/*************************
	 		 对List(列表)操作命令集合
		     LPUSH
		     LPUSHX
		     RPUSH
		     RPUSHX
		     LPOP
		     RPOP
		     BLPOP
		     BRPOP
		     LLEN
		     LRANGE
		     LREM
		     LSET
		     LTRIM
		     LINDEX
		     LINSERT
		     RPOPLPUSH
		     BRPOPLPUSH
	*************************/

	/**
	 * Add the string value to the head (LPUSH) or tail (RPUSH) of the list
	 * stored at key. If the key does not exist an empty list is created just
	 * before the append operation. If the key exists but is not a List an error
	 * is returned.
	 * <p>
	 * Time complexity: O(1)
	 *
	 * @see Jedis#lpush(String, String)
	 *
	 * @param key
	 * @param string
	 * @return Integer reply, specifically, the number of elements inside the
	 *         list after the push operation.
	 */
	public Long rpush(String key, String string) throws RedisException {
		Long result = 0L;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			result = jedis.rpush(key, string);
		} finally {
			releaseJedisInstance(jedis);
		}
		return result;
	}

	/**
	 * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。
	 * @Title: rpush
	 * @Description:
	 * @param <T>
	 * @param key
	 * @param oList
	 * @throws RedisException
	 */
	public <T> void rpush(String key, List<T> oList) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			for (Object o : oList) {
				String s = JsonUtil.Object2Json(o);
				jedis.rpush(key, s);
			}
		} catch (RedisException e) {
			throw e;
		} finally {
			releaseJedisInstance(jedis);
		}
	}

	/**
	 * Add the string value to the head (LPUSH) or tail (RPUSH) of the list
	 * stored at key. If the key does not exist an empty list is created just
	 * before the append operation. If the key exists but is not a List an error
	 * is returned.
	 * <p>
	 * Time complexity: O(1)
	 *
	 * @see Jedis#rpush(String, String)
	 *
	 * @param key
	 * @param string
	 * @return Integer reply, specifically, the number of elements inside the
	 *         list after the push operation.
	 */
	public Long lpush(String key, String string) throws RedisException {
		Long result = 0L;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			result = jedis.lpush(key, string);
		} finally {
			releaseJedisInstance(jedis);
		}
		return result;
	}

	/**
	 * Add the Object to the head (LPUSH) or tail (RPUSH) of the list
	 * stored at key. If the key does not exist an empty list is created just
	 * before the append operation. If the key exists but is not a List an error
	 * is returned.
	 * <p>
	 * Time complexity: O(1)
	 *
	 * @see Jedis#rpush(String, String)
	 *
	 * @param key
	 * @param 0 Object
	 * @return Integer reply, specifically, the number of elements inside the
	 *         list after the push operation.
	 */
	public Long lpush(String key, Object o) throws RedisException {
		Long result = 0L;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			String string = JsonUtil.Object2Json(o);

			result = jedis.lpush(key, string);
		} finally {
			releaseJedisInstance(jedis);
		}
		return result;
	}

	/**
	 * Add the Object list to the head (LPUSH) or tail (RPUSH) of the list
	 * stored at key. If the key does not exist an empty list is created just
	 * before the append operation. If the key exists but is not a List an error
	 * is returned.
	 * <p>
	 * Time complexity: O(1)
	 *
	 * @see Jedis#rpush(String, String)
	 *
	 * @param key
	 * @param oList list of object
	 */
	public <T> void lpush(String key, List<T> oList) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			for (T t : oList) {
				String string = JsonUtil.Object2Json(t);

				jedis.lpush(key, string);
			}
		} finally {
			releaseJedisInstance(jedis);
		}
	}

	/**
	 * 移除并返回列表 key 的头元素。
	 * @Title: lpop
	 * @Description:
	 * @param <T>
	 * @param key
	 * @param clazz
	 * @param size
	 * @return
	 * @throws RedisException
	 */
	public <T> List<T> lpop(String key, Class<T> clazz, int size) throws RedisException {
		String json = null;
		Jedis jedis = null;
		int count = 0;
		List<T> results = null;

		try {
			jedis = getJedis();
			long exists = jedis.llen(key);
			count = (int) (exists > size ? size : exists);

			if (count > 0) {
				results = new ArrayList<T>();
			}

			for (int i = 0; i < count; i++) {
				json = jedis.lpop(key);

				if (json != null) {
					results.add(JsonUtil.Json2Object(json, clazz));
				}
			}
		} catch (RedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				releaseJedisInstance(jedis);
			}
		}

		return results;
	}

	/**
	 * 移除并返回列表 key 的头元素。
	 * @Title: lpop
	 * @Description:
	 * @param key
	 * @return
	 * @throws RedisException
	 */
	public String lpop(String key) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.lpop(key);
		} catch (RedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				releaseJedisInstance(jedis);
			}
		}
	}

	/**
	 * 移除并返回列表 key 的尾元素。
	 * @Title: rpop
	 * @Description:
	 * @param key
	 * @return
	 * @throws RedisException
	 */
	public String rpop(String key) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.rpop(key);
		} catch (RedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				releaseJedisInstance(jedis);
			}
		}
	}

	/**
	 *
	 * @Title: blpop
	 * @Description:
	 * @param timeout
	 * @param key
	 * @return
	 * @throws RedisException
	 */
	public List<String> blpop(int timeout, String... key) throws RedisException {
		Jedis jedis = null;
		List<String> result = null;
		try {
			jedis = getJedis();
			result = jedis.blpop(timeout, key);
		} catch (RedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				releaseJedisInstance(jedis);
			}
		}
		return result;
	}

	/**
	 * 根据参数 count 的值，移除列表中与参数 value 相等的元素。
	 * count 的值可以是以下几种：
	 * count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
	 * count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
	 * count = 0 : 移除表中所有与 value 相等的值。
	 * @Title: lrem
	 * @Description:
	 * @param key
	 * @param count
	 * @param value
	 * @return
	 * @throws RedisException
	 */
	public Long lrem(String key, int count, String value) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.lrem(key, count, value);
		} catch (RedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				releaseJedisInstance(jedis);
			}
		}
	}

	/**
	 * 返回列表 key 中，下标为 index 的元素。
	 * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
	 * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
	 * 如果 key 不是列表类型，返回一个错误。
	 * @Title: lindex
	 * @Description:
	 * @param key
	 * @param index
	 * @return
	 * @throws RedisException
	 */
	public String lindex(String key, int index) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.lindex(key, index);
		} catch (RedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				releaseJedisInstance(jedis);
			}
		}
	}

	/**
	 * 将列表 key 下标为 index 的元素的值设置为 value 。当 index 参数超出范围，或对一个空列表( key 不存在)进行 LSET 时，返回一个错误。
	 * @Title: lset
	 * @Description:
	 * @param key
	 * @param index
	 * @param value
	 * @return
	 * @throws RedisException
	 */
	public String lset(String key, int index, String value) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.lset(key, index, value);
		} catch (RedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				releaseJedisInstance(jedis);
			}
		}
	}

	/**
	 * Return the length of the list stored at the specified key. If the key
	 * does not exist zero is returned (the same behaviour as for empty lists).
	 * If the value stored at key is not a list an error is returned.
	 * <p>
	 * Time complexity: O(1)
	 *
	 * @param key
	 * @return The length of the list.
	 */
	public Long llen(String key) throws RedisException {
		Long result = 0L;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			result = jedis.llen(key);
		} finally {
			releaseJedisInstance(jedis);
		}
		return result;
	}

	/**
	 * 将一个或多个值 value 插入到列表 key 的表头, 当列表大于指定长度是就对列表进行修剪(trim)
	 * @param key key
	 * @param start 开始 0
	 * @param end -1(全部) x 第几位
	 * @return
	 */
	public String ltrim(String key, int start, int end) throws RedisException {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			result = jedis.ltrim(key, start, end);
		} finally {
			releaseJedisInstance(jedis);
		}
		return result;
	}

	/**
	 * Return the specified elements of the list stored at the specified key.
	 * Start and end are zero-based indexes. 0 is the first element of the list
	 * (the list head), 1 the next element and so on.
	 * <p>
	 * For example LRANGE foobar 0 2 will return the first three elements of the
	 * list.
	 * <p>
	 * start and end can also be negative numbers indicating offsets from the
	 * end of the list. For example -1 is the last element of the list, -2 the
	 * penultimate element and so on.
	 * <p>
	 * <b>Consistency with range functions in various programming languages</b>
	 * <p>
	 * Note that if you have a list of numbers from 0 to 100, LRANGE 0 10 will
	 * return 11 elements, that is, rightmost item is included. This may or may
	 * not be consistent with behavior of range-related functions in your
	 * programming language of choice (think Ruby's Range.new, Array#slice or
	 * Python's range() function).
	 * <p>
	 * LRANGE behavior is consistent with one of Tcl.
	 * <p>
	 * <b>Out-of-range indexes</b>
	 * <p>
	 * Indexes out of range will not produce an error: if start is over the end
	 * of the list, or start > end, an empty list is returned. If end is over
	 * the end of the list Redis will threat it just like the last element of
	 * the list.
	 * <p>
	 * Time complexity: O(start+n) (with n being the length of the range and
	 * start being the start offset)
	 *
	 * @param key
	 * @param start
	 * @param end
	 * @return Multi bulk reply, specifically a list of elements in the
	 *         specified range.
	 */
	public List<String> lrange(String key, int start, int end) throws RedisException {
		List<String> result = new ArrayList<String>();
		Jedis jedis = null;
		try {
			jedis = getJedis();
			result = jedis.lrange(key, start, end);
		} finally {
			releaseJedisInstance(jedis);
		}
		return result;
	}

	public <T> List<T> lrange(String key, Class<T> clazz, int start, int end) throws RedisException {
		List<T> result = new ArrayList<T>();
		Jedis jedis = null;
		try {
			jedis = getJedis();
			List<String> strings = jedis.lrange(key, start, end);

			if (strings != null && !strings.isEmpty()) {
				for (final String string : strings) {
					result.add(JsonUtil.Json2Object(string, clazz));
				}
			}
		} finally {
			releaseJedisInstance(jedis);
		}
		return result;
	}

	public <T> T lrange(String key, int start, int end, TypeReference<T> clazz) throws RedisException {
		List<String> jsonList = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jsonList = jedis.lrange(key, start, end);
		} catch (RedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				releaseJedisInstance(jedis);
			}
		}
		if (jsonList == null || jsonList.isEmpty()) {
			return null;
		} else {
			return JsonUtil.Json2Object(jsonList.toString(), clazz);
		}

	}

	/*************************
	 		 对Set(集合)操作集合
		     SADD
		     SREM
		     SMEMBERS
		     SISMEMBER
		     SCARD
		     SMOVE
		     SPOP
		     SRANDMEMBER
		     SINTER
		     SINTERSTORE
		     SUNION
		     SUNIONSTORE
		     SDIFF
		     SDIFFSTORE
	*************************/

	/**
	 * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。假如 key 不存在，则创建一个只包含 member 元素作成员的集合。
	 * 当 key 不是集合类型时，返回一个错误。
	 * @Title: sadd
	 * @Description:
	 * @param key
	 * @param member
	 * @throws RedisException
	 */
	public Long sadd(String key, String member) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.sadd(key, member);
		} catch (RedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				releaseJedisInstance(jedis);
			}
		}
	}

	/**
	 * 返回集合 key 的基数(集合中元素的数量)
	 * @Title: scard
	 * @Description:
	 * @param key
	 * @return
	 * @throws RedisException
	 */
	public Long scard(String key) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.scard(key);
		} catch (RedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				releaseJedisInstance(jedis);
			}
		}
	}

	/**
	 * 返回集合 key 中的所有成员
	 * @Title: smembers
	 * @Description:
	 * @param key
	 * @return
	 * @throws RedisException
	 */
	public Set<String> smembers(String key) throws RedisException {
		Set<String> result = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			result = jedis.smembers(key);
		} catch (RedisException e) {
			throw e;
		} finally {
			releaseJedisInstance(jedis);
		}
		return result;
	}

	/**
	 * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。当 key 不是集合类型，返回一个错误。
	 * @Title: srem
	 * @Description:
	 * @param key
	 * @param member
	 */
	public void srem(String key, String member) {
		if (StringUtils.isEmpty(key) || StringUtils.isEmpty(member)) {
			return;
		}

		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.srem(key, member);
		} catch (RedisException e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				releaseJedisInstance(jedis);
			}
		}
	}

	/**
	 * 判断 member 元素是否集合 key 的成员。
	 * @Title: sismember
	 * @Description:
	 * @param key
	 * @param member
	 * @return
	 */
	public boolean sismember(String key, String member) {
		boolean ismember = false;
		if (StringUtils.isEmpty(key) || StringUtils.isEmpty(member)) {
			return ismember;
		}

		Jedis jedis = null;
		try {
			jedis = getJedis();
			ismember = jedis.sismember(key, member);
		} catch (RedisException e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				releaseJedisInstance(jedis);
			}
		}
		return ismember;
	}

	/*************************
			 有序集(Sorted set)
		     ZADD
		     ZREM
		     ZCARD
		     ZCOUNT
		     ZSCORE
		     ZINCRBY
		     ZRANGE
		     ZREVRANGE
		     ZRANGEBYSCORE
		     ZREVRANGEBYSCORE
		     ZRANK
		     ZREVRANK
		     ZREMRANGEBYRANK
		     ZREMRANGEBYSCORE
		     ZINTERSTORE
		     ZUNIONSTORE
	*************************/
	/**
	 * REVIEW
	 * @Description: 排序集合
	 * @param key
	 * @param score 根据此排序
	 * @param member    
	 * @author huan.wang@baidao.com  wanghuan  
	 * @throws RedisException 
	 * @date 2015年6月19日 下午7:13:50
	 */
	public Long zadd(String key, Long score, String member) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.zadd(key, score, member);
		} catch (RedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				releaseJedisInstance(jedis);
			}
		}
	}

	/**
	 * 
	 * REVIEW
	 * @Description: 删除Sorted set集合总的某一个元素
	 * @author feihu.qiu@baidao.com
	 * @param key
	 * @param member
	 * @return
	 * @throws RedisException
	 * @date 2016年5月24日 上午9:35:04
	 */
	public Long zrem(String key, String member) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.zrem(key, member);
		} catch (RedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				releaseJedisInstance(jedis);
			}
		}
	}

	/**
	 * 
	 * REVIEW
	 * @Description: 获取Sorted set集合元素的个数
	 * @author feihu.qiu@baidao.com
	 * @param key
	 * @return
	 * @throws RedisException
	 * @date 2016年5月24日 上午9:35:54
	 */
	public Long zcard(String key) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.zcard(key);
		} catch (RedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				releaseJedisInstance(jedis);
			}
		}
	}

	public String[] zrange(String key, int start, int end, boolean reverse) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			Set<String> set = jedis.zrange(key, start, end);
			if (set == null || set.size() == 0)
				return null;

			String[] values = set.toArray(new String[] {});
			if (reverse) {
				CollectionUtils.reverseArray(values);
			}

			return values;
		} catch (RedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				releaseJedisInstance(jedis);
			}
		}
	}

	/**
	 * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列。
	 * 具有相同 score 值的成员按字典序(lexicographical order)来排列(该属性是有序集提供的，不需要额外的计算)。
	 * 可选的 LIMIT 参数指定返回结果的数量及区间(就像SQL中的 SELECT LIMIT offset, count )，注意当 offset 很大时，定位 offset 的操作可能需要遍历整个有序集，此过程最坏复杂度为 O(N) 时间。
	 * @Title: zremrangeByScore
	 * @Description:
	 * @param key
	 * @param start
	 * @param end
	 * @throws RedisException
	 */
	public void zremrangeByScore(String key, Double start, Double end) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.zremrangeByScore(key, start, end);
		} catch (RedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				releaseJedisInstance(jedis);
			}
		}
	}

	/*************************
		 Pub/Sub(发布/订阅)
	     PUBLISH
	     SUBSCRIBE
	     PSUBSCRIBE
	     UNSUBSCRIBE
	     PUNSUBSCRIBE
	*************************/

	/**
	 * 将信息 message 发送到指定的频道 channel
	 * @Title: publish
	 * @Description:
	 * @param channel
	 * @param message
	 * @return
	 * @throws RedisException
	 */
	public Long publish(String channel, String message) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.publish(channel, message);
		} catch (RedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				releaseJedisInstance(jedis);
			}
		}
	}

	/**
	 * 订阅给定的一个或多个频道的信息
	 * @Title: subscribe
	 * @Description:
	 * @param jedisPubSub
	 * @param channels
	 * @throws RedisException
	 */
	public void subscribe(JedisPubSub jedisPubSub, String... channels) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.subscribe(jedisPubSub, channels);
		} catch (RedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				releaseJedisInstance(jedis);
			}
		}
	}

	/**
	 * @Description: check if field is an existing field in the hash stored at key
	 * @author xi.he@baidao.com
	 * @date 2014年2月25日 下午8:34:36
	 * @param key
	 * @param field
	 * @return true if the hash contains field.
	 * 			false if the hash does not contain field, or key does not exist.
	 * @throws RedisException
	 */
	public boolean hexists(String key, String field) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.hexists(key, field);
		} catch (RedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				releaseJedisInstance(jedis);
			}
		}
	}

	public <T> T hget(String key, String field, Class<T> clazz) throws RedisException {
		String json = this.hget(key, field);
		if (json == null) {
			return null;
		}
		return JsonUtil.Json2Object(json, clazz);
	}

	public <T> T hget(String key, String field, TypeReference<T> clazz) throws RedisException {
		String json = this.hget(key, field);
		if (json == null) {
			return null;
		}
		return JsonUtil.Json2Object(json, clazz);
	}

	public <T> List<T> hgetAllValues(String key, Class<T> clazz) throws RedisException {
		Map<String, String> jsonMap = this.hgetAll(key);
		if (jsonMap == null || jsonMap.isEmpty()) {
			return new ArrayList<T>(0);
		}
		List<T> result = new ArrayList<T>();
		Collection<String> jsons = jsonMap.values();
		for (String json : jsons) {
			result.add(JsonUtil.Json2Object(json, clazz));
		}
		return result;
	}

	/*************************
	 redis事物操作
	 watch
	 multi
	 exec
	 * @throws RedisException 
	 *************************/
	/**
	 * 
	 * REVIEW
	 * @Description:观察某个或多个key值，如果在执行的时候key对应的值发生变化，则命令不执行，返回false 。
	 * @param redisTransactionHandle
	 * @return
	 * @throws Exception    
	 * @author mengjie.liu@baidao.com  mengjie.liu  
	 * @date 2016年8月4日 下午6:35:54
	 */
	public Boolean exec(RedisTransactionHandle redisTransactionHandle) throws RedisException {
		Jedis jedis = null;
		List<Object> list = null;
		try {
			jedis = getJedis();
			redisTransactionHandle.watchKey(jedis);
			Boolean businessFlag = redisTransactionHandle.watchAfterBusiness();
			if (!businessFlag) {
				jedis.unwatch();
				return false;
			}
			Transaction transaction = jedis.multi();
			redisTransactionHandle.execMethd(transaction);
			list = transaction.exec();
			jedis.unwatch();
		} finally {
			releaseJedisInstance(jedis);
		}
		if (list == null) {
			return false;
		}
		return true;
	}

}
