package Controller.shiro;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import Controller.shiro.redis.RedisExcecption;
import Controller.shiro.redis.RedisManager;

public class RedisShiroCache<K, V> implements Cache<K, V> {

	private RedisManager<K, V> redisManager;

	public RedisShiroCache(RedisManager<K, V> redisManager) {
		super();
		this.redisManager = redisManager;
	}

	public V get(K key) throws CacheException {
		try {
			return redisManager.get(key);
		} catch (RedisExcecption e) {
			throw new CacheException(e);
		}
	}

	public V put(K key, V value) throws CacheException {
		try {
			redisManager.create(key, value);
			return value;
		} catch (RedisExcecption e) {
			throw new CacheException(e);
		}
	}

	public V remove(K key) throws CacheException {
		try {
			V v = redisManager.get(key);
			redisManager.delete(key);
			return v;
		} catch (RedisExcecption e) {
			throw new CacheException(e);
		}
	}

	public void clear() throws CacheException {
		throw new CacheException("不支持");
	}

	public int size() {
		throw new CacheException("不支持");
	}

	public Set<K> keys() {
		throw new CacheException("不支持");
	}

	public Collection<V> values() {
		throw new CacheException("不支持");
	}
}
