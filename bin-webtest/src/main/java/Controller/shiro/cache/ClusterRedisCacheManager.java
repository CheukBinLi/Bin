package Controller.shiro.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@SuppressWarnings({ "unchecked", "deprecation" })
public class ClusterRedisCacheManager<V> extends AbstractRedisCacheManager<ShardedJedis, V> {

	ShardedJedis tempJedis;
	private int maxIdle = 10;
	private int maxTotal = 300;
	private int maxWaitMillis = 5000;
	private int overTimeSecond = 60;
	// private String serverList = "192.168.3.5:6379";
	private String serverList = "127.0.0.1:6379";
	private String password = null;

	private static ShardedJedisPool pool;
	private JedisPoolConfig config;
	private List<JedisShardInfo> shardInfos;

	@Override
	ShardedJedis getResource() {
		return pool.getResource();
	}

	@Override
	void init() {
		// if (log.isInfoEnabled())
		// log.info("shiro-redis-cache:init");
		if (null != pool)
			return;
		config = new JedisPoolConfig();
		config.setMaxIdle(this.maxIdle);// 空闲
		config.setMaxTotal(this.maxTotal);
		config.setMaxWaitMillis(this.maxWaitMillis);
		if (null == this.serverList)
			return;
		shardInfos = new ArrayList<JedisShardInfo>();
		StringTokenizer ip = new StringTokenizer(serverList, ",");
		String[] split;
		JedisShardInfo info = null;
		// 列表 127.0.0.1:110,127.0.0.1:123
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
		// if (log.isInfoEnabled())
		// log.info("shiro-redis-cache:complete");
	}

	@Override
	void destory(ShardedJedis jedis) {
		pool.returnResourceObject(jedis);
	}

	public void update(Cache<String, V> cache) throws Throwable {
		(tempJedis = getResource()).setex(getSerialize().encode(cache.getKey()), overTimeSecond, getSerialize().encode(cache.getValue()));
		// (tempJedis = getResource()).set(getSerialize().encode(cache.getKey()), getSerialize().encode(cache.getValue()));
		destory(tempJedis);
	}

	public void delete(String k) throws Throwable {
		(tempJedis = getResource()).del(k.getBytes());
		destory(tempJedis);
	}

	public void create(Cache<String, V> cache) throws Throwable {
		update(cache);
	}

	public V getAndSet(Cache<String, V> cache) throws Throwable {
		try {
			return (V) (tempJedis = getResource()).getSet(getSerialize().encode(cache.getKey()), getSerialize().encode(cache.getValue()));
		} finally {
			destory(tempJedis);
		}
	}

	public <R> R get(String k) throws Throwable {
		try {
			Object o = getSerialize().decode(getResource().get(getSerialize().encode(k)));
			return null == o ? null : (R) o;
		} finally {
			destory(tempJedis);
		}
	}

	public void expire(Object key, int expireSeconds) throws Throwable {
		try {
			getResource().expire(getSerialize().encode(key), expireSeconds);
		} finally {
			destory(tempJedis);
		}
	}

	public Collection<Serializable> getcollection() throws Throwable {
		throw new Exception("不支持");
	}

	public ClusterRedisCacheManager() {
		init();
	}

}
