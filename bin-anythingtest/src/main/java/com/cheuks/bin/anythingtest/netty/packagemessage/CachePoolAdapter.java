package com.cheuks.bin.anythingtest.netty.packagemessage;

import io.netty.channel.Channel;

public class CachePoolAdapter {

	protected final static CachePoolAdapter newInstance = new CachePoolAdapter();

	protected CachePoolAdapter() {
	}

	public static CachePoolAdapter newInstance() {
		return newInstance;
	}

	public <T> T getObject(String key) {
		return DefaultCachePool.newInstance().getObject(key);
	}

	public Object putObject(String key, Object value) {
		return DefaultCachePool.newInstance().putObject(key, value);
	}

	public Object remove(String key) {
		return DefaultCachePool.newInstance().remove(key);
	}

	public boolean contain(String key) {
		return DefaultCachePool.newInstance().contain(key);
	}

	public <T> T getObject(Channel key) {
		return ChannelCachePool.newInstance().getObject(key);
	}

	public Object putObject(Channel key, Object value) {
		return ChannelCachePool.newInstance().putObject(key, value);
	}

	public Object remove(Channel key) {
		return ChannelCachePool.newInstance().remove(key);
	}

	public boolean contain(Channel key) {
		return ChannelCachePool.newInstance().contain(key);
	}

}
