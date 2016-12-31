package Controller.shiro.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

@SuppressWarnings({ "unchecked", "restriction" })
public abstract class ClusterJedisManager extends AbstractJedisManager<JedisCluster> {

	private static transient final Logger LOG = LoggerFactory.getLogger(ClusterJedisManager.class);

	private int maxIdle = 50;
	private int maxTotal = 300;
	private int maxWaitMillis = 5000;
	private int timeOut = 5000;
	private String serverList = "192.168.1.200:2000,192.168.1.201:2001,192.168.1.202:2002,192.168.1.203:2003,192.168.1.204:2004,192.168.1.205:2005";
	private boolean testOnBorrow;// ping
	private int expireSecond = 300;// 5分钟

	private JedisPoolConfig config;
	protected static JedisCluster jedisCluster;
	protected Set<HostAndPort> hosts;
	private volatile boolean isInit;

	@PostConstruct
	void init() {
		if (isInit)
			return;
		isInit = true;
		if (LOG.isInfoEnabled())
			LOG.info("ClusterJedisManager:init");
		hosts = new HashSet<HostAndPort>();
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
		StringTokenizer ip = new StringTokenizer(serverList, ",");
		String[] split;
		while (ip.hasMoreTokens()) {
			split = ip.nextToken().split(":");
			if (null == split || split.length < 2)
				continue;
			hosts.add(new HostAndPort(split[0], Integer.valueOf(split[1])));
		}
		jedisCluster = new JedisCluster(hosts, timeOut, config);
		if (null == getRedisSerialize())
			setRedisSerialize(new DefaultRedisSerialize());
		if (LOG.isInfoEnabled())
			LOG.info("ClusterJedisManager:complete");
	}

	public void deleteObject(Object k) throws RedisExcecption {
		JedisCluster jedis = getResource();
		try {
			jedis.del(getRedisSerialize().encode(k));
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

	public <R> R getAndSetObject(Object k, Object v) throws RedisExcecption {
		JedisCluster jedis = getResource();
		byte[] result;
		try {
			byte[] key = getRedisSerialize().encode(k);
			result = jedis.getSet(key, getRedisSerialize().encode(v));
			jedis.expire(key, expireSecond);
			return null != result ? (R) getRedisSerialize().decode(result) : null;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public void expire(Object key, int expireSeconds) throws RedisExcecption {
		JedisCluster jedis = getResource();
		try {
			getResource().expire(getRedisSerialize().encode(key), expireSeconds);
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public <R> R getObject(Object k) throws RedisExcecption {
		JedisCluster jedis = getResource();
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
	public JedisCluster getResource() {
		init();
		return jedisCluster;
	}

	public ClusterJedisManager setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
		return this;
	}

	public ClusterJedisManager setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
		return this;
	}

	public ClusterJedisManager setMaxWaitMillis(int maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
		return this;
	}

	public ClusterJedisManager setServerList(String serverList) {
		this.serverList = serverList;
		return this;
	}

	public ClusterJedisManager setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
		return this;
	}

	public ClusterJedisManager setExpireSecond(int expireSecond) {
		this.expireSecond = expireSecond;
		return this;
	}

	public boolean existsObject(Object key) throws RedisExcecption {
		try {
			return getResource().exists(getRedisSerialize().encode(key));
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	public boolean setObject(Object key, Object value) throws RedisExcecption {
		return setObject(key, value, expireSecond);
	}

	public boolean setObject(Object key, Object value, int expireSeconds) throws RedisExcecption {
		String result;
		try {
			result = getResource().setex(getRedisSerialize().encode(key), expireSeconds, getRedisSerialize().encode(value));
			if (LOG.isDebugEnabled())
				LOG.debug("set:" + result);
			return "OObject".equalsIgnoreCase(result);
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	public boolean incr(Object key, Integer value) throws RedisExcecption {
		Long result;
		try {
			result = getResource().incrBy(getRedisSerialize().encode(key), value);
			if (LOG.isDebugEnabled())
				LOG.debug("incr:" + result);
			return true;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	public boolean setMapObject(Object key, Object mapObjectey, Object value) throws RedisExcecption {
		Long result;
		try {
			result = getResource().hset(getRedisSerialize().encode(key), getRedisSerialize().encode(mapObjectey), getRedisSerialize().encode(value));
			if (LOG.isDebugEnabled())
				LOG.debug("setMap:" + result);
			return result > 0;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	public boolean setMapObjecteyExists(Object key, Object mapObjectey) throws RedisExcecption {
		boolean result;
		try {
			result = getResource().hexists(getRedisSerialize().encode(key), getRedisSerialize().encode(mapObjectey));
			if (LOG.isDebugEnabled())
				LOG.debug("setMapObjecteyExists:" + result);
			return result;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	public boolean incrByMap(Object key, Object mapObjectey, Integer value) throws RedisExcecption {
		Long result;
		try {
			result = getResource().hincrBy(getRedisSerialize().encode(key), getRedisSerialize().encode(mapObjectey), value);
			if (LOG.isDebugEnabled())
				LOG.debug("incr:" + result);
			return true;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	public <R> R getMapObjectalue(Object key, Object field) throws RedisExcecption {
		byte[] result;
		R r = null;
		try {
			result = getResource().hget(getRedisSerialize().encode(key), getRedisSerialize().encode(field));
			try {
				return null == result ? null : (r = (R) getRedisSerialize().decode(result));
			} finally {
				if (LOG.isDebugEnabled())
					LOG.debug("getMapObjectalue:" + r);
			}
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	public boolean mapRemove(Object key, Object... field) throws RedisExcecption {
		Long result;
		List<byte[]> fields = new ArrayList<byte[]>();
		if (null == field)
			return false;
		try {
			if (field.length > 1) {
				for (Object obj : field)
					fields.add(getRedisSerialize().encode(obj));
				result = getResource().hdel(getRedisSerialize().encode(key), (byte[][]) fields.toArray());
			} else
				result = getResource().hdel(getRedisSerialize().encode(key), getRedisSerialize().encode(field[0]));
			if (LOG.isDebugEnabled())
				LOG.debug("mapRemove:" + result);
			return true;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	public boolean addListFirst(Object key, Object... value) throws RedisExcecption {
		return addList(key, true, value);
	}

	public boolean addListLast(Object key, Object... value) throws RedisExcecption {
		return addList(key, false, value);
	}

	protected boolean addList(Object key, boolean isFirst, Object... value) throws RedisExcecption {
		Long result;
		List<byte[]> values = new ArrayList<byte[]>();
		if (null == value)
			return false;
		try {
			if (value.length > 1) {
				for (Object obj : value)
					values.add(getRedisSerialize().encode(obj));
				if (isFirst)
					result = getResource().lpush(getRedisSerialize().encode(key), (byte[][]) values.toArray());
				else
					result = getResource().rpush(getRedisSerialize().encode(key), (byte[][]) values.toArray());

			} else {
				if (isFirst)
					result = getResource().lpush(getRedisSerialize().encode(key), getRedisSerialize().encode(value[0]));
				else
					result = getResource().rpush(getRedisSerialize().encode(key), getRedisSerialize().encode(value[0]));
			}
			if (LOG.isDebugEnabled())
				LOG.debug(isFirst ? "addListFirst:" : "addListLast:" + result);
			return true;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	public boolean removeListWithoutFor(Object key, int start, int end) throws RedisExcecption {
		String result;
		try {
			result = getResource().ltrim(getRedisSerialize().encode(key), start, end);
			if (LOG.isDebugEnabled())
				LOG.debug("removeListWithoutFor:" + result);
			return true;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	public boolean setListIndexObject(Object key, int index, Object value) throws RedisExcecption {
		String result;
		try {
			result = getResource().lset(getRedisSerialize().encode(key), index, getRedisSerialize().encode(value));
			if (LOG.isDebugEnabled())
				LOG.debug("setListIndex:" + result);
			return true;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	public long listLenObject(Object key) throws RedisExcecption {
		Long result;
		try {
			result = getResource().llen(getRedisSerialize().encode(key));
			if (LOG.isDebugEnabled())
				LOG.debug("incr:" + result);
			return result;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	public <R> R popListFirstObject(Object key) throws RedisExcecption {
		return popList(key, true);
	}

	public <R> R popListLastObject(Object key) throws RedisExcecption {
		return popList(key, false);
	}

	protected <R> R popList(Object key, boolean isFirst) throws RedisExcecption {
		byte[] result;
		R r = null;
		try {
			if (isFirst)
				result = getResource().lpop(getRedisSerialize().encode(key));
			else
				result = getResource().rpop(getRedisSerialize().encode(key));
			try {
				return null == result ? null : (r = (R) getRedisSerialize().decode(result));
			} finally {
				if (LOG.isDebugEnabled())
					LOG.debug(isFirst ? "popListFirst:" : "popListLast:" + r);
			}
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	public List<byte[]> get(Object... key) throws RedisExcecption {
		throw new RedisExcecption("nothing support");
	}

	public List<byte[]> getListByBytes(Object key, int start, int end) throws RedisExcecption {
		List<byte[]> result;
		try {
			result = getResource().lrange(getRedisSerialize().encode(key), start, end);
			if (LOG.isDebugEnabled())
				LOG.debug("getListByBytes:" + result);
			return result;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	public List<String> getListByString(String key, int start, int end) throws RedisExcecption {
		List<String> result;
		try {
			result = getResource().lrange(key, start, end);
			if (LOG.isDebugEnabled())
				LOG.debug("getListByString:" + result);
			return result;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	// public static void main(String[] args) throws RedisExcecption {
	// RedisManager rm = new ClusterJedisManager();
	// rm.set("普通SET", "普通SET");
	// rm.setMap("MAP", "A", "A");
	// rm.addListFirst("LIST", 1);
	// rm.addListFirst("LIST", 2);
	// rm.addListFirst("LIST", 3);
	// System.out.println("MAP:" + rm.getMapObjectalue("MAP", "A"));
	// System.out.println("LIST:" + rm.getListByBytes("LIST", 0, -1));
	// }

}
