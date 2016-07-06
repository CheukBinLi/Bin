package Controller.shiro.redis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class ClusterJedisManager<K extends Serializable, V extends Serializable> extends AbstractJedisManager<ShardedJedis, K, V> {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private int maxIdle = 8;
	private int maxTotal = 300;
	private int maxWaitMillis = 5000;
	private String serverList = "127.0.0.1:6379";
	private String password = null;
	private boolean testOnBorrow;// ping
	private int expireSecond = 300;// 5分钟

	private static ShardedJedisPool pool;
	private JedisPoolConfig config;
	private List<JedisShardInfo> shardInfos;

	void init() {
		if (log.isInfoEnabled())
			log.info("ClusterJedisManager:init");
		if (null != pool)
			return;
		config = new JedisPoolConfig();
		config.setMaxIdle(this.maxIdle);
		config.setMaxTotal(this.maxTotal);
		config.setMaxWaitMillis(this.maxWaitMillis);
		config.setTestOnBorrow(testOnBorrow);
		if (null == this.serverList) {
			if (log.isWarnEnabled())
				log.warn("servlet is null.");
			return;
		}
		shardInfos = new ArrayList<JedisShardInfo>();
		StringTokenizer ip = new StringTokenizer(serverList, ",");
		String[] split;
		JedisShardInfo info = null;
		while (ip.hasMoreTokens()) {
			split = ip.nextToken().split(":");
			if (null == split || split.length < 2)
				continue;
			info = new JedisShardInfo(split[0], split[1]);
			if (null != password)
				info.setPassword(password);
			shardInfos.add(info);
		}
		pool = new ShardedJedisPool(config, shardInfos);
		if (log.isInfoEnabled())
			log.info("ClusterJedisManager:complete");
	}

	public void delete(K k) throws RedisExcecption {
		ShardedJedis jedis = getResource();
		try {
			jedis.del(getRedisSerialize().encode(k));
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public void create(K k, V v) throws RedisExcecption {
		create(k, v, this.expireSecond);
	}

	public void create(K k, V v, int expireSeconds) throws RedisExcecption {
		ShardedJedis jedis = getResource();
		try {
			jedis.setex(getRedisSerialize().encode(k), expireSecond, getRedisSerialize().encode(v));
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public Collection<?> getcollection() throws RedisExcecption {
		return null;
	}

	@SuppressWarnings("unchecked")
	public V getAndSet(K k, V v) throws RedisExcecption {
		ShardedJedis jedis = getResource();
		byte[] result;
		try {
			result = jedis.getSet(getRedisSerialize().encode(k), getRedisSerialize().encode(v));
			return null != result ? (V) getRedisSerialize().decode(result) : null;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public void expire(K key, int expireSeconds) throws RedisExcecption {
		ShardedJedis jedis = getResource();
		try {
			getResource().expire(getRedisSerialize().encode(key), expireSeconds);
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	@SuppressWarnings("unchecked")
	public <R> R get(K k) throws RedisExcecption {
		ShardedJedis jedis = getResource();
		byte[] result;
		try {
			result = jedis.get(getRedisSerialize().encode(k));
			return null != result ? (R) getRedisSerialize().decode(result) : null;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	@Override
	ShardedJedis getResource() {
		return pool.getResource();
	}

	@Override
	void destory(ShardedJedis jedis) {

	}

	public ClusterJedisManager<K, V> setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
		return this;
	}

	public ClusterJedisManager<K, V> setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
		return this;
	}

	public ClusterJedisManager<K, V> setMaxWaitMillis(int maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
		return this;
	}

	public ClusterJedisManager<K, V> setServerList(String serverList) {
		this.serverList = serverList;
		return this;
	}

	public ClusterJedisManager<K, V> setPassword(String password) {
		this.password = password;
		return this;
	}

	public ClusterJedisManager<K, V> setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
		return this;
	}

	public ClusterJedisManager<K, V> setExpireSecond(int expireSecond) {
		this.expireSecond = expireSecond;
		return this;
	}

}
