package Controller.shiro.cache;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.shiro.ShiroException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Initializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

public class ShiroRedisManager implements CacheManager, Initializable {

	private final transient Logger log = LoggerFactory.getLogger(ShiroRedisManager.class);

	private final static BlockingDeque<Jedis> pool = new LinkedBlockingDeque<Jedis>();

	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		return null;
	}

	public void init() throws ShiroException {

	}

}
