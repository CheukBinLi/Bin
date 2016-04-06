package com.cheuks.bin.anythingtest.netty.packagemessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public class ResultCachePool implements CachePool {

	protected final static ResultCachePool newInstance = new ResultCachePool() {
	};

	protected ResultCachePool() {
	}

	public static ResultCachePool newInstance() {
		return newInstance;
	}

	private static final Map<String, Object> result = new ConcurrentHashMap<String, Object>();

	public <T> T getObject(String key) {
		Object o = result.remove(key);
		return null == o ? null : (T) o;
	}

	public Object putObject(String key, Object value) {
		return result.put(key, value);
	}

	public Object remove(String key) {
		return result.remove(key);
	}

	public boolean contain(String key) {
		return false;
	}

}
