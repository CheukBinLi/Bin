package com.cheuks.bin.anythingtest.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;

public class T1<K, V> extends ConcurrentHashMap<K, Object> {

	private ReferenceQueue<K> queue = new ReferenceQueue<K>();

	private void checkValues() {
		Entry<K, V> entry;
		for (Object x; (x = queue.poll()) != null;) {
			// entry = (T1<K, V>.Entry<K, V>) x;
			System.err.println(x);
			removeQueue(x);
			// removeEntry();
		}
	}

	class Entry<K, V> extends SoftReference<K> {
		private K key;
		private V value;

		public Entry(K key, V value, ReferenceQueue<? super K> q) {
			super(key, q);
			this.value = value;
			this.key = key;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}
	}

	protected Object removeQueue(Object key) {
		return super.remove(key);
	}

	@Override
	public int size() {
		checkValues();
		return super.size();
	}

	@Override
	public boolean isEmpty() {
		checkValues();
		return super.isEmpty();
	}

	@Override
	public Object get(Object key) {
		checkValues();
		Object o = super.get(key);
		if (null != o)
			return ((Entry<K, V>) o).getValue();
		return null;
	}

	@Override
	public boolean containsKey(Object key) {
		checkValues();
		return super.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		checkValues();
		return super.containsValue(value);
	}

	@Override
	public Object put(K key, Object value) {
		checkValues();
		return super.put(key, new Entry<K, Object>(key, value, queue));
	}

	@Override
	public void putAll(Map<? extends K, ? extends Object> m) {
		checkValues();
		super.putAll(m);
	}

	@Override
	public Object remove(Object key) {
		checkValues();
		return super.remove(key);
	}

	@Override
	public void clear() {
		checkValues();
		super.clear();
	}

	@Override
	public java.util.concurrent.ConcurrentHashMap.KeySetView<K, Object> keySet() {
		checkValues();
		return super.keySet();
	}

	@Override
	public Collection<Object> values() {
		checkValues();
		return super.values();
	}

	@Override
	public Set<java.util.Map.Entry<K, Object>> entrySet() {
		checkValues();
		return super.entrySet();
	}

	@Override
	public int hashCode() {
		checkValues();
		return super.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		checkValues();
		return super.equals(o);
	}

	@Override
	public Object putIfAbsent(K key, Object value) {
		checkValues();
		return super.putIfAbsent(key, value);
	}

	@Override
	public boolean remove(Object key, Object value) {
		checkValues();
		return super.remove(key, value);
	}

	@Override
	public boolean replace(K key, Object oldValue, Object newValue) {
		checkValues();
		return super.replace(key, oldValue, newValue);
	}

	@Override
	public Object replace(K key, Object value) {
		checkValues();
		return super.replace(key, value);
	}

	@Override
	public Object getOrDefault(Object key, Object defaultValue) {
		checkValues();
		return super.getOrDefault(key, defaultValue);
	}

	@Override
	public void forEach(BiConsumer<? super K, ? super Object> action) {
		checkValues();
		super.forEach(action);
	}

	@Override
	public void replaceAll(BiFunction<? super K, ? super Object, ? extends Object> function) {
		checkValues();
		super.replaceAll(function);
	}

	@Override
	public Object computeIfAbsent(K key, Function<? super K, ? extends Object> mappingFunction) {
		checkValues();
		return super.computeIfAbsent(key, mappingFunction);
	}

	@Override
	public Object computeIfPresent(K key, BiFunction<? super K, ? super Object, ? extends Object> remappingFunction) {
		checkValues();
		return super.computeIfPresent(key, remappingFunction);
	}

	@Override
	public Object compute(K key, BiFunction<? super K, ? super Object, ? extends Object> remappingFunction) {
		checkValues();
		return super.compute(key, remappingFunction);
	}

	@Override
	public Object merge(K key, Object value, BiFunction<? super Object, ? super Object, ? extends Object> remappingFunction) {
		checkValues();
		return super.merge(key, value, remappingFunction);
	}

	@Override
	public boolean contains(Object value) {
		checkValues();
		return super.contains(value);
	}

	@Override
	public Enumeration<K> keys() {
		checkValues();
		return super.keys();
	}

	@Override
	public Enumeration<Object> elements() {
		checkValues();
		return super.elements();
	}

	@Override
	public long mappingCount() {
		checkValues();
		return super.mappingCount();
	}

	@Override
	public java.util.concurrent.ConcurrentHashMap.KeySetView<K, Object> keySet(Object mappedValue) {
		checkValues();
		return super.keySet(mappedValue);
	}

	@Override
	public void forEach(long parallelismThreshold, BiConsumer<? super K, ? super Object> action) {
		checkValues();
		super.forEach(parallelismThreshold, action);
	}

	@Override
	public <U> void forEach(long parallelismThreshold, BiFunction<? super K, ? super Object, ? extends U> transformer, Consumer<? super U> action) {
		checkValues();
		super.forEach(parallelismThreshold, transformer, action);
	}

	@Override
	public <U> U search(long parallelismThreshold, BiFunction<? super K, ? super Object, ? extends U> searchFunction) {
		checkValues();
		return super.search(parallelismThreshold, searchFunction);
	}

	@Override
	public <U> U reduce(long parallelismThreshold, BiFunction<? super K, ? super Object, ? extends U> transformer, BiFunction<? super U, ? super U, ? extends U> reducer) {
		checkValues();
		return super.reduce(parallelismThreshold, transformer, reducer);
	}

	@Override
	public double reduceToDouble(long parallelismThreshold, ToDoubleBiFunction<? super K, ? super Object> transformer, double basis, DoubleBinaryOperator reducer) {
		checkValues();
		return super.reduceToDouble(parallelismThreshold, transformer, basis, reducer);
	}

	@Override
	public long reduceToLong(long parallelismThreshold, ToLongBiFunction<? super K, ? super Object> transformer, long basis, LongBinaryOperator reducer) {
		checkValues();
		return super.reduceToLong(parallelismThreshold, transformer, basis, reducer);
	}

	@Override
	public int reduceToInt(long parallelismThreshold, ToIntBiFunction<? super K, ? super Object> transformer, int basis, IntBinaryOperator reducer) {
		checkValues();
		return super.reduceToInt(parallelismThreshold, transformer, basis, reducer);
	}

	@Override
	public void forEachKey(long parallelismThreshold, Consumer<? super K> action) {
		checkValues();
		super.forEachKey(parallelismThreshold, action);
	}

	@Override
	public <U> void forEachKey(long parallelismThreshold, Function<? super K, ? extends U> transformer, Consumer<? super U> action) {
		checkValues();
		super.forEachKey(parallelismThreshold, transformer, action);
	}

	@Override
	public <U> U searchKeys(long parallelismThreshold, Function<? super K, ? extends U> searchFunction) {
		checkValues();
		return super.searchKeys(parallelismThreshold, searchFunction);
	}

	@Override
	public K reduceKeys(long parallelismThreshold, BiFunction<? super K, ? super K, ? extends K> reducer) {
		checkValues();
		return super.reduceKeys(parallelismThreshold, reducer);
	}

	@Override
	public <U> U reduceKeys(long parallelismThreshold, Function<? super K, ? extends U> transformer, BiFunction<? super U, ? super U, ? extends U> reducer) {
		checkValues();
		return super.reduceKeys(parallelismThreshold, transformer, reducer);
	}

	@Override
	public double reduceKeysToDouble(long parallelismThreshold, ToDoubleFunction<? super K> transformer, double basis, DoubleBinaryOperator reducer) {
		checkValues();
		return super.reduceKeysToDouble(parallelismThreshold, transformer, basis, reducer);
	}

	@Override
	public long reduceKeysToLong(long parallelismThreshold, ToLongFunction<? super K> transformer, long basis, LongBinaryOperator reducer) {
		checkValues();
		return super.reduceKeysToLong(parallelismThreshold, transformer, basis, reducer);
	}

	@Override
	public int reduceKeysToInt(long parallelismThreshold, ToIntFunction<? super K> transformer, int basis, IntBinaryOperator reducer) {
		checkValues();
		return super.reduceKeysToInt(parallelismThreshold, transformer, basis, reducer);
	}

	@Override
	public void forEachValue(long parallelismThreshold, Consumer<? super Object> action) {
		checkValues();
		super.forEachValue(parallelismThreshold, action);
	}

	@Override
	public <U> void forEachValue(long parallelismThreshold, Function<? super Object, ? extends U> transformer, Consumer<? super U> action) {
		checkValues();
		super.forEachValue(parallelismThreshold, transformer, action);
	}

	@Override
	public <U> U searchValues(long parallelismThreshold, Function<? super Object, ? extends U> searchFunction) {
		checkValues();
		return super.searchValues(parallelismThreshold, searchFunction);
	}

	@Override
	public Object reduceValues(long parallelismThreshold, BiFunction<? super Object, ? super Object, ? extends Object> reducer) {
		checkValues();
		return super.reduceValues(parallelismThreshold, reducer);
	}

	@Override
	public <U> U reduceValues(long parallelismThreshold, Function<? super Object, ? extends U> transformer, BiFunction<? super U, ? super U, ? extends U> reducer) {
		checkValues();
		return super.reduceValues(parallelismThreshold, transformer, reducer);
	}

	@Override
	public double reduceValuesToDouble(long parallelismThreshold, ToDoubleFunction<? super Object> transformer, double basis, DoubleBinaryOperator reducer) {
		checkValues();
		return super.reduceValuesToDouble(parallelismThreshold, transformer, basis, reducer);
	}

	@Override
	public long reduceValuesToLong(long parallelismThreshold, ToLongFunction<? super Object> transformer, long basis, LongBinaryOperator reducer) {
		checkValues();
		return super.reduceValuesToLong(parallelismThreshold, transformer, basis, reducer);
	}

	@Override
	public int reduceValuesToInt(long parallelismThreshold, ToIntFunction<? super Object> transformer, int basis, IntBinaryOperator reducer) {
		checkValues();
		return super.reduceValuesToInt(parallelismThreshold, transformer, basis, reducer);
	}

	@Override
	public void forEachEntry(long parallelismThreshold, Consumer<? super java.util.Map.Entry<K, Object>> action) {
		checkValues();
		super.forEachEntry(parallelismThreshold, action);
	}

	@Override
	public <U> void forEachEntry(long parallelismThreshold, Function<java.util.Map.Entry<K, Object>, ? extends U> transformer, Consumer<? super U> action) {
		checkValues();
		super.forEachEntry(parallelismThreshold, transformer, action);
	}

	@Override
	public <U> U searchEntries(long parallelismThreshold, Function<java.util.Map.Entry<K, Object>, ? extends U> searchFunction) {
		checkValues();
		return super.searchEntries(parallelismThreshold, searchFunction);
	}

	@Override
	public java.util.Map.Entry<K, Object> reduceEntries(long parallelismThreshold, BiFunction<java.util.Map.Entry<K, Object>, java.util.Map.Entry<K, Object>, ? extends java.util.Map.Entry<K, Object>> reducer) {
		checkValues();
		return super.reduceEntries(parallelismThreshold, reducer);
	}

	@Override
	public <U> U reduceEntries(long parallelismThreshold, Function<java.util.Map.Entry<K, Object>, ? extends U> transformer, BiFunction<? super U, ? super U, ? extends U> reducer) {
		checkValues();
		return super.reduceEntries(parallelismThreshold, transformer, reducer);
	}

	@Override
	public double reduceEntriesToDouble(long parallelismThreshold, ToDoubleFunction<java.util.Map.Entry<K, Object>> transformer, double basis, DoubleBinaryOperator reducer) {
		checkValues();
		return super.reduceEntriesToDouble(parallelismThreshold, transformer, basis, reducer);
	}

	@Override
	public long reduceEntriesToLong(long parallelismThreshold, ToLongFunction<java.util.Map.Entry<K, Object>> transformer, long basis, LongBinaryOperator reducer) {
		checkValues();
		return super.reduceEntriesToLong(parallelismThreshold, transformer, basis, reducer);
	}

	@Override
	public int reduceEntriesToInt(long parallelismThreshold, ToIntFunction<java.util.Map.Entry<K, Object>> transformer, int basis, IntBinaryOperator reducer) {
		checkValues();
		return super.reduceEntriesToInt(parallelismThreshold, transformer, basis, reducer);
	}

}
