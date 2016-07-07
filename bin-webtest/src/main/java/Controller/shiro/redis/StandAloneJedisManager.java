package Controller.shiro.redis;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class StandAloneJedisManager<K, V> extends AbstractJedisManager<Jedis, K, V> {

	private JedisPoolConfig config;
	private JedisPool pool;
	private int timeOut = 10000;
	private String password;
	private String host;
	private int port;
	private boolean testOnBorrow;// ping
	private int expireSecond = 300;// 5分钟

	@PostConstruct
	void init() {
		config = new JedisPoolConfig();
		config.setTestOnBorrow(testOnBorrow);
		pool = new JedisPool(config, host, port, timeOut, password);
	}

	public void delete(K k) throws RedisExcecption {
		Jedis jedis = getResource();
		try {
			jedis.del(getRedisSerialize().encode(k));
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public void create(K k, V v) throws RedisExcecption {
		create(k, v, expireSecond);
	}

	public void create(K k, V v, int expireSeconds) throws RedisExcecption {
		Jedis jedis = getResource();
		try {
			jedis.setex(getRedisSerialize().encode(k), expireSeconds, getRedisSerialize().encode(v));
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public <T> Collection<T> getcollection() throws RedisExcecption {
		throw new RedisExcecption("not supper getcollection() method.");
	}

	@SuppressWarnings("unchecked")
	public V getAndSet(K k, V v) throws RedisExcecption {
		Jedis jedis = getResource();
		byte[] result;
		try {
			byte[] key = getRedisSerialize().encode(k);
			result = jedis.getSet(key, getRedisSerialize().encode(v));
			jedis.expire(key, expireSecond);
			return null == result ? null : (V) getRedisSerialize().decode(result);
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public void expire(K key, int expireSeconds) throws RedisExcecption {
		Jedis jedis = getResource();
		try {
			jedis.expire(getRedisSerialize().encode(key), expireSeconds);
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	@SuppressWarnings("unchecked")
	public <R> R get(K k) throws RedisExcecption {
		Jedis jedis = getResource();
		byte[] result;
		try {
			result = jedis.get(getRedisSerialize().encode(k));
			return null == result ? null : (R) getRedisSerialize().decode(result);
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	@Override
	Jedis getResource() {
		return pool.getResource();
	}

	@Override
	void destory(Jedis jedis) {}

	public StandAloneJedisManager<K, V> setTimeOut(int timeOut) {
		this.timeOut = timeOut;
		return this;
	}

	public StandAloneJedisManager<K, V> setPassword(String password) {
		this.password = password;
		return this;
	}

	public StandAloneJedisManager<K, V> setHost(String host) {
		this.host = host;
		return this;
	}

	public StandAloneJedisManager<K, V> setPort(int port) {
		this.port = port;
		return this;
	}

	public StandAloneJedisManager<K, V> setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
		return this;
	}

	public StandAloneJedisManager<K, V> setExpireSecond(int expireSecond) {
		this.expireSecond = expireSecond;
		return this;
	}

	public StandAloneJedisManager() {
		super();
		init();
	}

}
