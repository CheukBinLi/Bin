package com.cheuks.bin.anythingtest.CacheDemo.redis;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import Controller.shiro.redis.AbstractJedisManager;
import Controller.shiro.redis.DefaultRedisSerialize;
import Controller.shiro.redis.RedisExcecption;
import Controller.shiro.redis.RedisManager;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class ClusterTest<K extends Serializable, V extends Serializable> extends AbstractJedisManager<JedisCluster, K, V> {

	protected Set<HostAndPort> hosts = new HashSet<HostAndPort>();
	protected JedisCluster jedisCluster;

	public ClusterTest() {
		super();
		// hosts.add(new HostAndPort("192.168.3.8", 7001));
		hosts.add(new HostAndPort("192.168.3.8", 7002));
		hosts.add(new HostAndPort("192.168.3.8", 7003));
		hosts.add(new HostAndPort("192.168.3.8", 7004));
		hosts.add(new HostAndPort("192.168.3.8", 7005));
		hosts.add(new HostAndPort("192.168.3.8", 7006));
		jedisCluster = new JedisCluster(hosts, 2000, 10);
		setRedisSerialize(new DefaultRedisSerialize());
	}

	public void delete(K k) throws RedisExcecption {

	}

	public boolean create(K k, V v) throws RedisExcecption {
		return false;
	}

	public boolean create(K k, V v, int expireSeconds) throws RedisExcecption {
		try {
			getResource().setex(getRedisSerialize().encode(k), expireSeconds, getRedisSerialize().encode(v));
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			// destory(jedisCluster);
		}
		return false;
	}

	public <T> Collection<T> getcollection() throws RedisExcecption {
		return null;
	}

	public V getAndSet(K k, V v) throws RedisExcecption {
		return null;
	}

	public void expire(K key, int expireSeconds) throws RedisExcecption {
	}

	public <R> R get(K k) throws RedisExcecption {
		try {
			return (R) getRedisSerialize().decode(getResource().get(getRedisSerialize().encode(k)));
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			// destory(jedisCluster);
		}
	}

	@Override
	JedisCluster getResource() {
		return jedisCluster;
	}

	@Override
	void destory(JedisCluster jedis) {
	}

	public static void main(String[] args) throws RedisExcecption {

		RedisManager<String, String> rm = new ClusterTest<String, String>();
		rm.create("AAAAAAAAAAAA", "XXXXXXXXXXXXXX", 360);
		System.err.println(rm.get("AAAAAAAAAAAA"));

	}

}
