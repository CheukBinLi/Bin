package com.cheuks.bin.anythingtest.netty.packagemessage;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public class ChannelCachePool implements CachePool<Channel> {
	private static final Map<Channel, Object> pool = new ConcurrentHashMap<Channel, Object>();

	protected final static CachePool<Channel> newInstance = new ChannelCachePool() {
	};

	protected ChannelCachePool() {
	}

	public static CachePool<Channel> newInstance() {
		return newInstance;
	}

	public <T> T getObject(Channel key) {
		Object o = pool.remove(key);
		return null == o ? null : (T) o;
	}

	public Object putObject(Channel key, Object value) {
		return pool.put(key, value);
	}

	public Object remove(Channel key) {
		return pool.remove(key);
	}

	public boolean contain(Channel key) {
		return pool.containsKey(key);
	}

}
