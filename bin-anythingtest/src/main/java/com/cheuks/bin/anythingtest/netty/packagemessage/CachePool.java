package com.cheuks.bin.anythingtest.netty.packagemessage;

public interface CachePool<K> {

	/***
	 * 返回对象
	 * @param key
	 * @return
	 */
	<T> T getObject(K key);

	/***
	 * 添加对象
	 * @param key
	 * @param value
	 * @return 返回有KEY空间对象/null
	 */
	Object putObject(K key, Object value);

	/***
	 * 移除对象
	 * @param key
	 * @return 返回被移除的对象/null
	 */
	Object remove(K key);

	/***
	 * 检查对象是否存在
	 * @param key
	 * @return 
	 */
	boolean contain(K key);

}