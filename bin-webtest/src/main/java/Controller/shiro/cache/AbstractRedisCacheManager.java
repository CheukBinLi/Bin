package Controller.shiro.cache;

import redis.clients.jedis.JedisCommands;

public abstract class AbstractRedisCacheManager<J extends JedisCommands, V> implements CacheManager<String, V> {

	private Serialize serialize;

	abstract J getResource();

	abstract void init();

	abstract void destory(J jedis);

	public Serialize getSerialize() {
		return serialize;
	}

	public void setSerialize(Serialize serialize) {
		this.serialize = serialize;
	}

}
