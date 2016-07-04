package Controller.shiro.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.shiro.ShiroException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

public class ShiroRedisClusterManager implements CacheManager, Initializable {

	private final transient Logger log = LoggerFactory.getLogger(ShiroRedisClusterManager.class);

	private int maxIdle = 10;
	private int maxTotal = 300;
	private int maxWaitMillis = 5000;
	// private String serverList = "192.168.3.5:6379";
	private String serverList = "127.0.0.1:6379";
	// private String name = null;
	private String password = null;

	private static ShardedJedisPool pool;
	private JedisPoolConfig config;
	private List<JedisShardInfo> shardInfos;
	private Serialize serializable = new DefaultSerialize();

	private Map<String, ShiroRedisClusterCache<Object, ? extends Serializable>> cache = new ConcurrentHashMap<String, ShiroRedisClusterCache<Object, ? extends Serializable>>();

	public ShiroRedisClusterCache<?, ? extends Serializable> getX() {
		if (null == serializable)
			serializable = new DefaultSerialize();
		return new ShiroRedisClusterCache<Object, Serializable>(pool, serializable);

	}

	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		ShiroRedisClusterCache<?, ?> tempCache = cache.get(name);
		if (null == tempCache) {
			tempCache = new ShiroRedisClusterCache<K, Serializable>(pool, serializable);
		}
		return (Cache<K, V>) tempCache;
	}

	public ShiroRedisClusterManager() {
		super();
		init();
	}

	public void init() throws ShiroException {
		if (log.isInfoEnabled())
			log.info("shiro-redis-cache:init");
		if (null != pool)
			return;
		config = new JedisPoolConfig();
		config.setMaxIdle(maxIdle);// 空闲
		config.setMaxTotal(maxTotal);
		config.setMaxWaitMillis(maxWaitMillis);
		if (null == serverList)
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
		if (log.isInfoEnabled())
			log.info("shiro-redis-cache:complete");
	}

	public static void main(String[] args) {
		ShiroRedisClusterManager srm = new ShiroRedisClusterManager();
		ShiroRedisClusterCache cache = srm.getX();
		cache.put("AAAA", "叼嗱星");
		System.out.println(cache.get("AAAA"));
		// cache.clear();
	}

}
