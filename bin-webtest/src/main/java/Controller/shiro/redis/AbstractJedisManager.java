package Controller.shiro.redis;

import redis.clients.jedis.JedisCommands;

public abstract class AbstractJedisManager<J extends JedisCommands> implements RedisManager {

	/***
	 * jedis
	 * 
	 * @return
	 */
	public abstract J getResource();

	/***
	 * 销毁连接
	 * 
	 * @param jedis
	 */
	public void destory(J jedis) {
	};

	/***
	 * 序列化
	 */
	private RedisSerialize redisSerialize;

	public RedisSerialize getRedisSerialize() {
		return redisSerialize;
	}

	public AbstractJedisManager<J> setRedisSerialize(RedisSerialize redisSerialize) {
		this.redisSerialize = redisSerialize;
		return this;
	}

}
