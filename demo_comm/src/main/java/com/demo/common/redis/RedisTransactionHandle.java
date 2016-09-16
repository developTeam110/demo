package com.demo.common.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/** 
 * REVIEW
 * @Description: 
 * @author mengjie.liu@baidao.com mengjie.liu
 * @date 2016年8月4日 下午6:18:30 
 *  
 */

public interface RedisTransactionHandle {

	/**
	 * 
	 * REVIEW
	 * @Description:观察某个key值
	 * 例子 jedis.watch("redisW"); 
	 * @param jedis    
	 * @author mengjie.liu@baidao.com  mengjie.liu  
	 * @date 2016年8月4日 下午6:25:39
	 */
	void watchKey(Jedis jedis);

	/**
	 * 
	 * REVIEW
	 * @Description:需要执行的方法
	 * 例子 	transaction.set("redisW", "lmj1");     
	 * @author mengjie.liu@baidao.com  mengjie.liu  
	 * @date 2016年8月4日 下午6:26:31
	 */
	void execMethd(Transaction transaction);

	/**
	 * 
	 * REVIEW
	 * @Description:执行watch后的业务代码 
	 * @return false 不执行接下来的代码，true执行接下来的代码    
	 * @author mengjie.liu@baidao.com  mengjie.liu  
	 * @date 2016年8月4日 下午7:14:40
	 */
	Boolean watchAfterBusiness();

}
