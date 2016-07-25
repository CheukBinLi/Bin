package Controller.shiro.redis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class ClusterJedisManager<K extends Serializable, V extends Serializable> extends AbstractJedisManager<ShardedJedis, K, V> {

	private static transient final Logger LOG = LoggerFactory.getLogger(ClusterJedisManager.class);

	private int maxIdle = 8;
	private int maxTotal = 300;
	private int maxWaitMillis = 5000;
	private String serverList;
	private String password;
	private boolean testOnBorrow;// ping
	private int expireSecond = 300;// 5分钟

	private static ShardedJedisPool pool;
	private JedisPoolConfig config;
	private List<JedisShardInfo> shardInfos;

	private volatile boolean isInit;

	@PostConstruct
	void init() {
		if (isInit)
			return;
		isInit = true;
		if (LOG.isInfoEnabled())
			LOG.info("ClusterJedisManager:init");
		config = new JedisPoolConfig();
		config.setMaxIdle(this.maxIdle);
		config.setMaxTotal(this.maxTotal);
		config.setMaxWaitMillis(this.maxWaitMillis);
		config.setTestOnBorrow(testOnBorrow);
		if (null == this.serverList) {
			if (LOG.isWarnEnabled())
				LOG.warn("servlet is null.");
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
			info = new JedisShardInfo(split[0], Integer.valueOf(split[1]));
			if (null != password)
				info.setPassword(password);
			shardInfos.add(info);
		}
		pool = new ShardedJedisPool(config, shardInfos);
		if (LOG.isInfoEnabled())
			LOG.info("ClusterJedisManager:complete");
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

	public boolean create(K k, V v) throws RedisExcecption {
		return create(k, v, this.expireSecond);
	}

	public boolean create(K k, V v, int expireSeconds) throws RedisExcecption {
		ShardedJedis jedis = getResource();
		try {
			String o = jedis.setex(getRedisSerialize().encode(k), expireSeconds, getRedisSerialize().encode(v));
			return o.equalsIgnoreCase("OK");
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public <T> Collection<T> getcollection() throws RedisExcecption {
		LOG.error("no supper getcollection() method.");
		return null;
	}

	@SuppressWarnings("unchecked")
	public V getAndSet(K k, V v) throws RedisExcecption {
		ShardedJedis jedis = getResource();
		byte[] result;
		try {
			byte[] key = getRedisSerialize().encode(k);
			result = jedis.getSet(key, getRedisSerialize().encode(v));
			jedis.expire(key, expireSecond);
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
	public ShardedJedis getResource() {
		init();
		return pool.getResource();
	}

	@Override
	public void destory(ShardedJedis jedis) {
		jedis.close();
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
