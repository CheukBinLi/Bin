package Controller.shiro.cache;

import java.io.Serializable;

import redis.clients.jedis.JedisCommands;

public abstract class AbstractRedisCacheManager<J extends JedisCommands> implements CacheManager<String, Serializable> {

	Serialize serialize;

	J tempJedis;

	public void update(Cache<String, Serializable> cache) throws Throwable {
		(tempJedis = getResource()).set(cache.getKey(), new String(serialize.encode(cache.getValue())));
		destory(tempJedis);
	}

	public void delete(String k) throws Throwable {
		(tempJedis = getResource()).del(k);
		destory(tempJedis);
	}

	public void create(Cache<String, Serializable> cache) throws Throwable {
		update(cache);
	}

	public Serializable getAndSet(Cache<String, Serializable> cache) throws Throwable {
		try {
			return (tempJedis = getResource()).getSet(cache.getKey(), new String(serialize.encode(cache.getValue())));
		} finally {
			destory(tempJedis);
		}
	}

	public Serializable get(String k) throws Throwable {
		try {
			return (tempJedis = getResource()).get(k);
		} finally {
			destory(tempJedis);
		}
	}

	abstract J getResource();

	abstract void init();

	abstract void destory(J jedis);

	public AbstractRedisCacheManager() {
		super();
		init();
	}

	public Serialize getSerialize() {
		return serialize;
	}

	public void setSerialize(Serialize serialize) {
		this.serialize = serialize;
	}

}
