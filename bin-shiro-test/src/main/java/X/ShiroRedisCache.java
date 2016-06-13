package X;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class ShiroRedisCache<K, V extends Serializable> implements Cache<K, V> {

	private ShardedJedisPool pool;

	private ShiroSerializable serializable;

	public ShiroRedisCache(ShardedJedisPool pool, ShiroSerializable serializable) {
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
			if (null != jedis)
				jedis.close();
		}
	}

	public V put(K key, V value) throws CacheException {
		ShardedJedis jedis = pool.getResource();
		String result = null;
		try {
			result = jedis.set(serializable.encode(key), serializable.encode(value));
		} catch (Throwable e) {
			throw new CacheException(e);
		} finally {
			if (null != jedis)
				jedis.close();
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
			if (null != jedis)
				jedis.close();
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
			if (null != jedis)
				jedis.close();
		}
	}

	public int size() {
		return 0;
	}

	public Set<K> keys() {
		ShardedJedis jedis = pool.getResource();
		Set<String> set = new HashSet();
		try {
			synchronized (pool) {
				Iterator<Jedis> it = jedis.getAllShards().iterator();
				while (it.hasNext())
					set.addAll(it.next().keys("*"));
			}
		} finally {
			if (null != jedis)
				jedis.close();
		}
		return (Set<K>) set;
	}

	public Collection<V> values() {
		//		ShardedJedis jedis = pool.getResource();
		//		Set<String> set = new HashSet();
		//		List<Entry<K, V>>list;
		//		try {
		//			synchronized (pool) {
		//				Iterator<Jedis> it = jedis.getAllShards().iterator();
		//				Iterator<String> keys;
		//				while (it.hasNext()) {
		//					keys = it.next().keys("*").iterator();
		//					while (keys.hasNext())
		//						jedis.get(keys.next());
		//
		//				}
		//			}
		//		} finally {
		//			if (null != jedis)
		//				jedis.close();
		//		}
		//		return (Set<K>) set;
		return null;
	}

}
