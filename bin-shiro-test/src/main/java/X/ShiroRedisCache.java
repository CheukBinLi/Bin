package X;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

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
		} finally {
			if (null != jedis)
				jedis.close();
		}
	}

	public V put(K key, V value) throws CacheException {
		ShardedJedis jedis = pool.getResource();
		String result = null;
		try {
			//			Object o = serializable.decode(jedis.get(serializable.encode(key)));
			result = jedis.set(serializable.encode(key), serializable.encode(value));
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
		Long result = -1L;
		Object o = null;
		try {
			result = jedis.del(serializable.encode(key));
		} finally {
			if (null != jedis)
				jedis.close();
		}
		if (result == -1L)
			throw new CacheException("remove [" + key + "] fial!");
		return null;
	}

	public void clear() throws CacheException {
		ShardedJedis jedis = pool.getResource();
		Long result = -1L;
		Object o = null;
		try {
			result = jedis
		} finally {
			if (null != jedis)
				jedis.close();
		}
		if (result == -1L)
			throw new CacheException("remove [" + key + "] fial!");
	}

	public int size() {
		return 0;
	}

	public Set<K> keys() {
		return null;
	}

	public Collection<V> values() {
		return null;
	}

}
