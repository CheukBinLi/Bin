package Controller.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import Controller.shiro.redis.RedisManager;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class RedisShiroCacheManager implements CacheManager {

	private RedisManager redisManager;

	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		return new RedisShiroCache<K, V>(redisManager);
	}

	public void setRedisManager(RedisManager redisManager) {
		this.redisManager = redisManager;
	}
}
