package Controller.shiro.redis;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import redis.clients.jedis.JedisCluster;

@SuppressWarnings({ "unchecked" })
public abstract class AbstractRedisClusterBinaryManager<T extends JedisCluster> implements RedisManager {

	private int expireSecond = 1800;// 30分钟
	private String encoding = "UTF-8";

	public abstract T getResource();

	public abstract Logger getLog();

	public abstract void destory(T jedis);

	private RedisSerialize redisSerialize;

	public RedisSerialize getRedisSerialize() {
		return redisSerialize;
	}

	public AbstractRedisClusterBinaryManager<T> setRedisSerialize(RedisSerialize redisSerialize) {
		this.redisSerialize = redisSerialize;
		return this;
	}

	public void delete(byte[] key) throws RedisExcecption {
		T jedis = getResource();
		Long result;
		try {
			result = jedis.del(key);
			if (getLog().isDebugEnabled())
				getLog().debug("delete:" + result);
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public boolean exists(byte[] key) throws RedisExcecption {
		T jedis = getResource();
		boolean result;
		try {
			result = jedis.exists(key);
			if (getLog().isDebugEnabled())
				getLog().debug("exists:" + result);
			return result;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public boolean set(byte[] key, byte[] value) throws RedisExcecption {
		getResource().set(key, value);
		return set(key, value, expireSecond);
	}

	public boolean incr(byte[] key, Integer value) throws RedisExcecption {
		T jedis = getResource();
		Long result;
		try {
			result = jedis.incrBy(key, value);
			if (getLog().isDebugEnabled())
				getLog().debug("incr:" + result);
			return result > 0;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public boolean set(byte[] key, byte[] value, int expireSeconds) throws RedisExcecption {
		T jedis = getResource();
		String result;
		try {
			result = jedis.setex(key, expireSeconds, value);
			if (getLog().isDebugEnabled())
				getLog().debug("set:" + result);
			return "OK".equalsIgnoreCase(result);
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public byte[] getAndSet(byte[] key, byte[] value) throws RedisExcecption {
		T jedis = getResource();
		byte[] result;
		try {
			result = jedis.getSet(key, value);
			jedis.expire(key, expireSecond);
			try {
				return null == result ? null : result;
			} finally {
				if (getLog().isDebugEnabled())
					getLog().debug("get:List<byte[]>:" + result);
			}
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public byte[] get(byte[] key) throws RedisExcecption {
		T jedis = getResource();
		byte[] result;
		try {
			result = jedis.get(key);
			try {
				return null == result ? null : result;
			} finally {
				if (getLog().isDebugEnabled())
					getLog().debug("get:byte[]:" + result);
			}
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public List<byte[]> get(byte[]... keys) throws RedisExcecption {
		T jedis = getResource();
		List<byte[]> result;
		try {
			result = jedis.mget(keys);
			try {
				return result;
			} finally {
				if (getLog().isDebugEnabled())
					getLog().debug("get:List<byte[]>:" + result);
			}
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public boolean setMap(byte[] key, byte[] mapKey, byte[] value) throws RedisExcecption {
		T jedis = getResource();
		Long result;
		try {
			result = jedis.hset(key, mapKey, value);
			if (getLog().isDebugEnabled())
				getLog().debug("setMap:" + result);
			return result > 0;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public boolean setMap(byte[] key, Map<byte[], byte[]> map) throws RedisExcecption {
		T jedis = getResource();
		String result;
		try {
			result = jedis.hmset(key, map);
			if (getLog().isDebugEnabled())
				getLog().debug("setMap:" + result);
			return "OK".equalsIgnoreCase(result);
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public boolean mapKeyExists(byte[] key, byte[] mapKey) throws RedisExcecption {
		T jedis = getResource();
		boolean result;
		try {
			result = jedis.hexists(key, mapKey);
			if (getLog().isDebugEnabled())
				getLog().debug("mapKeyExists:" + result);
			return result;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public boolean incrByMap(byte[] key, byte[] mapKey, Integer value) throws RedisExcecption {
		T jedis = getResource();
		Long result;
		try {
			result = jedis.hincrBy(key, mapKey, value);
			if (getLog().isDebugEnabled())
				getLog().debug("incrByMap:" + result);
			return result > 0;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public byte[] getMapValue(byte[] key, byte[] mapKey) throws RedisExcecption {
		T jedis = getResource();
		byte[] result;
		try {
			result = jedis.hget(key, mapKey);
			try {
				return null == result ? null : result;
			} finally {
				if (getLog().isDebugEnabled())
					getLog().debug("getMapValue:" + result);
			}
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public boolean mapRemove(byte[] key, byte[]... mapKeys) throws RedisExcecption {
		T jedis = getResource();
		Long result;
		try {
			result = jedis.hdel(key, mapKeys);
			if (getLog().isDebugEnabled())
				getLog().debug("mapRemove:" + result);
			return result > 0;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public List<byte[]> getListByBytes(byte[] key, int start, int end) throws RedisExcecption {
		T jedis = getResource();
		List<byte[]> result;
		try {
			result = jedis.lrange(key, start, end);
			if (getLog().isDebugEnabled())
				getLog().debug("mapRemove-list:size:" + result.size());
			return result;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public boolean addListFirst(byte[] key, byte[]... value) throws RedisExcecption {
		return addList(key, true, value);
	}

	public boolean addListLast(byte[] key, byte[]... value) throws RedisExcecption {
		return addList(key, false, value);
	}

	protected boolean addList(byte[] key, boolean isFirst, byte[]... value) throws RedisExcecption {
		T jedis = getResource();
		Long result;
		try {
			if (isFirst)
				result = jedis.lpush(key, value);
			else
				result = jedis.rpush(key, value);
			if (getLog().isDebugEnabled())
				getLog().debug("addList" + (isFirst ? "First:" : "Last:") + result);
			return result > 0;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	protected boolean addList(String key, boolean isFirst, String... value) throws RedisExcecption {
		T jedis = getResource();
		Long result;
		try {
			if (isFirst)
				result = jedis.lpush(key, value);
			else
				result = jedis.rpush(key, value);
			if (getLog().isDebugEnabled())
				getLog().debug("addList" + (isFirst ? "First:" : "Last:") + result);
			return result > 0;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public boolean removeListWithoutFor(byte[] key, int start, int end) throws RedisExcecption {
		T jedis = getResource();
		String result;
		try {
			result = jedis.ltrim(key, start, end);
			if (getLog().isDebugEnabled())
				getLog().debug("removeListWithoutFor:" + result);
			return "OK".equalsIgnoreCase(result);
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public boolean setListIndex(byte[] key, int index, byte[] value) throws RedisExcecption {
		T jedis = getResource();
		String result;
		try {
			result = getResource().lset(key, index, value);
			if (getLog().isDebugEnabled())
				getLog().debug("setListIndex:" + result);
			return "OK".equalsIgnoreCase(result);
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public long listLen(byte[] key) throws RedisExcecption {
		T jedis = getResource();
		Long result;
		try {
			result = jedis.llen(key);
			if (getLog().isDebugEnabled())
				getLog().debug("incr:" + result);
			return result;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public byte[] popListFirst(byte[] key) throws RedisExcecption {
		return popList(key, true);
	}

	public byte[] popListLast(byte[] key) throws RedisExcecption {
		return popList(key, false);
	}

	protected byte[] popList(byte[] key, boolean isFirst) throws RedisExcecption {
		T jedis = getResource();
		byte[] result;
		try {
			if (isFirst)
				result = jedis.lpop(key);
			else
				result = jedis.rpop(key);
			if (getLog().isDebugEnabled())
				getLog().debug(isFirst ? "popListFirst:" : "popListLast:" + result);
			return result;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	protected String popList(String key, boolean isFirst) throws RedisExcecption {
		T jedis = getResource();
		String result;
		try {
			if (isFirst)
				result = jedis.lpop(key);
			else
				result = jedis.rpop(key);
			if (getLog().isDebugEnabled())
				getLog().debug(isFirst ? "popListFirst:" : "popListLast:" + result);
			return result;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	/** ################## */

	public void delete(String key) throws RedisExcecption {
		try {
			this.delete(key.getBytes(encoding));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public boolean exists(String key) throws RedisExcecption {
		try {
			return this.exists(key.getBytes(encoding));
		} catch (UnsupportedEncodingException e) {
			throw new RedisExcecption(e);
		}
	}

	public boolean set(String key, String value) throws RedisExcecption {
		try {
			return this.set(key.getBytes(encoding), value.getBytes(encoding));
		} catch (UnsupportedEncodingException e) {
			throw new RedisExcecption(e);
		}
	}

	public boolean incr(String key, Integer value) throws RedisExcecption {
		try {
			return this.incr(key.getBytes(encoding), value);
		} catch (UnsupportedEncodingException e) {
			throw new RedisExcecption(e);
		}
	}

	public boolean set(String key, String value, int expireSeconds) throws RedisExcecption {
		try {
			return this.set(key.getBytes(encoding), value.getBytes(encoding), expireSeconds);
		} catch (UnsupportedEncodingException e) {
			throw new RedisExcecption(e);
		}
	}

	public String getAndSet(String key, String value) throws RedisExcecption {
		byte[] result;
		try {
			result = this.getAndSet(key.getBytes(encoding), value.getBytes(encoding));
			return null == result ? null : new String(result, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new RedisExcecption(e);
		}
	}

	public String get(String key) throws RedisExcecption {
		byte[] result;
		try {
			result = this.get(key.getBytes(encoding));
			return null == result ? null : new String(result, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new RedisExcecption(e);
		}
	}

	public List<String> get(String... key) throws RedisExcecption {
		T jedis = getResource();
		List<String> result;
		try {
			result = jedis.mget(key);
			try {
				return null == result ? null : result;
			} finally {
				if (getLog().isDebugEnabled())
					getLog().debug("get:List<String>:" + result);
			}
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public boolean setMap(String key, String mapKey, String value) throws RedisExcecption {
		try {
			return this.setMap(key.getBytes(encoding), mapKey.getBytes(encoding), value.getBytes(encoding));
		} catch (UnsupportedEncodingException e) {
			throw new RedisExcecption(e);
		}
	}

	public boolean setMap(String key, Map<String, String> map) throws RedisExcecption {
		T jedis = getResource();
		String result;
		try {
			result = jedis.hmset(key, map);
			if (getLog().isDebugEnabled())
				getLog().debug("incr:" + result);
			return "OK".equalsIgnoreCase(result);
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public boolean mapKeyExists(String key, String mapKey) throws RedisExcecption {
		try {
			return this.mapKeyExists(key.getBytes(encoding), mapKey.getBytes(encoding));
		} catch (UnsupportedEncodingException e) {
			throw new RedisExcecption(e);
		}
	}

	public boolean incrByMap(String key, String mapKey, Integer value) throws RedisExcecption {
		try {
			return this.incrByMap(key.getBytes(encoding), mapKey.getBytes(encoding), value);
		} catch (UnsupportedEncodingException e) {
			throw new RedisExcecption(e);
		}
	}

	public String getMapValue(String key, String mapKey) throws RedisExcecption {
		T jedis = getResource();
		String result;
		try {
			result = jedis.hget(key, mapKey);
			if (getLog().isDebugEnabled())
				getLog().debug("getMapValue:" + result);
			return result;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public boolean mapRemove(String key, String... mapKey) throws RedisExcecption {
		T jedis = getResource();
		Long result;
		try {
			result = jedis.hdel(key, mapKey);
			if (getLog().isDebugEnabled())
				getLog().debug("incr:" + result);
			return result > 0;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public List<String> getListByString(String key, int start, int end) throws RedisExcecption {
		T jedis = getResource();
		List<String> result;
		try {
			result = jedis.lrange(key, start, end);
			if (getLog().isDebugEnabled())
				getLog().debug("incr:" + result);
			return result;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public boolean addListFirst(String key, String... value) throws RedisExcecption {
		return addList(key, true, value);
	}

	public boolean addListLast(String key, String... value) throws RedisExcecption {
		return addList(key, false, value);
	}

	public boolean removeListWithoutFor(String key, int start, int end) throws RedisExcecption {
		T jedis = getResource();
		String result;
		try {
			result = jedis.ltrim(key, start, end);
			if (getLog().isDebugEnabled())
				getLog().debug("incr:" + result);
			return "OK".equalsIgnoreCase(result);
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public boolean setListIndex(String key, int index, String value) throws RedisExcecption {
		try {
			return this.setListIndex(key.getBytes(encoding), index, value.getBytes(encoding));
		} catch (UnsupportedEncodingException e) {
			throw new RedisExcecption(e);
		}
	}

	public long listLen(String key) throws RedisExcecption {
		try {
			return this.listLen(key.getBytes(encoding));
		} catch (UnsupportedEncodingException e) {
			throw new RedisExcecption(e);
		}
	}

	public String getListIndex(String key, int index) throws RedisExcecption {
		T jedis = getResource();
		String result;
		try {
			result = jedis.lindex(key, index);
			if (getLog().isDebugEnabled())
				getLog().debug("incr:" + result);
			return result;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public byte[] getListIndex(byte[] key, int index) throws RedisExcecption {
		T jedis = getResource();
		byte[] result;
		try {
			result = jedis.lindex(key, index);
			if (getLog().isDebugEnabled())
				getLog().debug("incr:" + result);
			return result;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public String popListFirst(String key) throws RedisExcecption {
		return popList(key, true);
	}

	public String popListLast(String key) throws RedisExcecption {
		return popList(key, false);
	}

	/*********************/

	public boolean setObject(String key, Object value) throws RedisExcecption {
		try {
			return this.set(key.getBytes(getEncoding()), getRedisSerialize().encode(value));
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	public boolean setObject(String key, Object value, int expireSeconds) throws RedisExcecption {
		try {
			return this.set(key.getBytes(getEncoding()), getRedisSerialize().encode(value), expireSeconds);
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	public <R> R getAndSetObject(String key, Object value) throws RedisExcecption {
		byte[] result;
		try {
			result = this.getAndSet(key.getBytes(getEncoding()), getRedisSerialize().encode(value));
			return null == result ? null : (R) getRedisSerialize().decode(result);
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	public <R> R getObject(String key) throws RedisExcecption {
		byte[] result;
		try {
			result = this.get(key.getBytes(getEncoding()));
			return null == result ? null : (R) getRedisSerialize().decode(result);
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	public boolean setMapObject(String key, Object mapKey, Object value) throws RedisExcecption {
		try {
			return this.setMap(key.getBytes(getEncoding()), getRedisSerialize().encode(mapKey), getRedisSerialize().encode(value));
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	public Map<byte[], byte[]> getMapObject(String key) throws RedisExcecption {
		T jedis = getResource();
		Map<byte[], byte[]> result;
		try {
			result = jedis.hgetAll(key.getBytes(getEncoding()));
			if (getLog().isDebugEnabled())
				getLog().debug("getMapObject-size:" + result.size());
			return result;
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			destory(jedis);
		}
	}

	public boolean mapKeyExistsObject(String key, Object mapKey) throws RedisExcecption {
		try {
			return this.mapKeyExists(key.getBytes(getEncoding()), getRedisSerialize().encode(mapKey));
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	public boolean addListFirstObject(String key, Object value) throws RedisExcecption {
		try {
			return this.addListFirst(key.getBytes(getEncoding()), getRedisSerialize().encode(value));
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	public boolean addListLastObject(String key, Object value) throws RedisExcecption {
		try {
			return this.addListLast(key.getBytes(getEncoding()), getRedisSerialize().encode(value));
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	public <R> R getListIndexObject(String key, int index) throws RedisExcecption {
		byte[] result;
		try {
			result = this.getListIndex(key.getBytes(getEncoding()), index);
			return null == result ? null : (R) getRedisSerialize().decode(result);
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	public boolean setListIndexObject(String key, int index, Object value) throws RedisExcecption {
		try {
			return this.setListIndex(key.getBytes(getEncoding()), index, getRedisSerialize().encode(value));
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	public long listLenObject(String key) throws RedisExcecption {
		try {
			return this.listLen(key.getBytes(getEncoding()));
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	public <R> R popListFirstObject(String key) throws RedisExcecption {
		byte[] result;
		try {
			result = this.popListFirst(key.getBytes(getEncoding()));
			return null == result ? null : (R) getRedisSerialize().decode(result);
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	public <R> R popListLastObject(String key) throws RedisExcecption {
		byte[] result;
		try {
			result = this.popListLast(key.getBytes(getEncoding()));
			return null == result ? null : (R) getRedisSerialize().decode(result);
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		}
	}

	public AbstractRedisClusterBinaryManager<?> setEncoding(String encoding) {
		this.encoding = encoding;
		return this;
	}

	public int getExpireSecond() {
		return expireSecond;
	}

	public void setExpireSecond(int expireSecond) {
		this.expireSecond = expireSecond;
	}

	public String getEncoding() {
		return encoding;
	}

}
