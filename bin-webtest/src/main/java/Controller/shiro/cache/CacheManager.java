package Controller.shiro.cache;

import java.util.Collection;

public interface CacheManager<K /* extends Serializable */, V /* extends Serializable */> {

	public void update(Cache<K, V> cache) throws Throwable;

	public void delete(K k) throws Throwable;

	public void create(Cache<K, V> cache) throws Throwable;

	public Collection<?> getcollection() throws Throwable;

	public V getAndSet(Cache<K, V> cache) throws Throwable;

	public V get(K k) throws Throwable;

}
