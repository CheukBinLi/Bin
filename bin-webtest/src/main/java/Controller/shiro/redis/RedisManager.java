package Controller.shiro.redis;

import java.util.Collection;

public interface RedisManager<K, V> {

	public void delete(K k) throws RedisExcecption;

	public void create(K k, V v) throws RedisExcecption;

	public void create(K k, V v, int expireSeconds) throws RedisExcecption;

	public Collection<?> getcollection() throws RedisExcecption;

	public V getAndSet(K k, V v) throws RedisExcecption;

	public void expire(K key, int expireSeconds) throws RedisExcecption;

	public <R> R get(K k) throws RedisExcecption;
}
