package Controller.shiro.redis;

import redis.clients.jedis.JedisCommands;

public abstract class AbstractJedisManager<J extends JedisCommands, K, V> implements RedisManager<K, V> {

	/***
	 * jedis
	 * 
	 * @return
	 */
	abstract J getResource();

	/***
	 * 销毁连接
	 * 
	 * @param jedis
	 */
	abstract void destory(J jedis);

	/***
	 * 序列化
	 */
	private RedisSerialize redisSerialize;

	public RedisSerialize getRedisSerialize() {
		return redisSerialize;
	}

	public AbstractJedisManager<J, K, V> setRedisSerialize(RedisSerialize redisSerialize) {
		this.redisSerialize = redisSerialize;
		return this;
	}

}
