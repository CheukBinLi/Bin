package Controller.shiro.cache;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@SuppressWarnings({ "deprecation", "unchecked" })
public class ShiroRedisClusterCache<K, V extends Serializable> implements Cache<K, V> {

	private ShardedJedisPool pool;

	private Serialize serializable;

	private int overTimeSceond = 120;

	public ShiroRedisClusterCache(ShardedJedisPool pool, Serialize serializable) {
		super();
		this.pool = pool;
		this.serializable = serializable;
	}

	public V get(K key) throws CacheException {
		ShardedJedis jedis = pool.getResource();
		try {
			Object o = serializable.decode(jedis.get(serializable.encode(key)));
			return null != o ? (V) o : null;
		} catch (Throwable e) {
			throw new CacheException(e);
		} finally {
			pool.returnResource(jedis);
			// if (null != jedis)
			// jedis.close();
		}
	}

	public V put(K key, V value) throws CacheException {
		ShardedJedis jedis = pool.getResource();
		String result = null;
		try {
			// result = jedis.set(serializable.encode(key), serializable.encode(value));
			result = jedis.setex(serializable.encode(key), this.overTimeSceond, serializable.encode(value));
		} catch (Throwable e) {
			throw new CacheException(e);
		} finally {
			pool.returnResource(jedis);
			// if (null != jedis)
			// jedis.close();
		}
		if (null == result)
			throw new CacheException("setting [" + key + "] fial!");
		return value;
	}

	public V remove(K key) throws CacheException {
		ShardedJedis jedis = pool.getResource();
		Object result = null;
		try {
			result = jedis.del(serializable.encode(key));
		} catch (Throwable e) {
			throw new CacheException(e);
		} finally {
			pool.returnResource(jedis);
			// if (null != jedis)
			// jedis.close();
		}
		return null != result ? (V) result : null;
	}

	public void clear() throws CacheException {
		ShardedJedis jedis = pool.getResource();
		try {
			synchronized (pool) {
				Iterator<Jedis> it = jedis.getAllShards().iterator();
				while (it.hasNext())
					it.next().flushDB();
			}
		} finally {
			pool.returnResource(jedis);
			// if (null != jedis)
			// jedis.close();
		}
	}

	public int size() {
		return -1;
	}

	public Set<K> keys() {
		ShardedJedis jedis = pool.getResource();
		Set<String> set = new HashSet<String>();
		try {
			synchronized (pool) {
				Iterator<Jedis> it = jedis.getAllShards().iterator();
				while (it.hasNext())
					set.addAll(it.next().keys("*"));
			}
		} finally {
			pool.returnResource(jedis);
			// if (null != jedis)
			// jedis.close();
		}
		return (Set<K>) set;
	}

	public Collection<V> values() {
		// ShardedJedis jedis = pool.getResource();
		// Set<String> set = new HashSet();
		// List<Entry<K, V>>list;
		// try {
		// synchronized (pool) {
		// Iterator<Jedis> it = jedis.getAllShards().iterator();
		// Iterator<String> keys;
		// while (it.hasNext()) {
		// keys = it.next().keys("*").iterator();
		// while (keys.hasNext())
		// jedis.get(keys.next());
		//
		// }
		// }
		// } finally {
		// if (null != jedis)
		// jedis.close();
		// }
		// return (Set<K>) set;
		return null;
	}

}
