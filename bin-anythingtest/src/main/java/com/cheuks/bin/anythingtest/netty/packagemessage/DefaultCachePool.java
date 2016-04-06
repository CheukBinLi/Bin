package com.cheuks.bin.anythingtest.netty.packagemessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public abstract class DefaultCachePool implements CachePool {

	private final Map<String, Object> pool = new ConcurrentHashMap<String, Object>();

	protected final static CachePool newInstance = new DefaultCachePool() {
	};

	protected DefaultCachePool() {
	}

	public static CachePool newInstance() {
		return newInstance;
	}

	/*
	 * (non-Javadoc)
	 * @see com.cheuks.bin.anythingtest.netty.packagemessage.CachePool#getObject(java.lang.String)
	 */
	public <T> T getObject(String key) {
		Object o = pool.get(key);
		return null == o ? null : (T) o;
	}

	/*
	 * (non-Javadoc)
	 * @see com.cheuks.bin.anythingtest.netty.packagemessage.CachePool#putObject(java.lang.String, java.lang.Object)
	 */
	public Object putObject(String key, Object value) {
		return pool.put(key, value);
	}

	/*
	 * (non-Javadoc)
	 * @see com.cheuks.bin.anythingtest.netty.packagemessage.CachePool#remove(java.lang.String)
	 */
	public Object remove(String key) {
		return pool.remove(key);
	}

	/*
	 * (non-Javadoc)
	 * @see com.cheuks.bin.anythingtest.netty.packagemessage.CachePool#contain(java.lang.String)
	 */
	public boolean contain(String key) {
		return pool.containsKey(key);
	}

}
