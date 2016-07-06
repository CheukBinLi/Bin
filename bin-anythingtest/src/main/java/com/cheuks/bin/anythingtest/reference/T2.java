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

import com.cheuks.bin.anythingtest.reference.T2.SoftNode;

public class T2<K, V> extends ConcurrentHashMap<K, SoftNode<K, V>> {

	private final ReferenceQueue<Object> queue = new ReferenceQueue<Object>();

	private void checkValues() {
		for (Object x; (x = queue.poll()) != null;) {
			// entry = (T1<K, V>.Entry<K, V>) x;
			SoftNode<K, V> o = (SoftNode<K, V>) x;
			Node<K, V> n = o.get();
			System.err.println(n);
			System.err.println(x);
		}
	}

	public static class Node<K, V> {
		private K key;
		private V value;

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public Node(K key, V value) {
			super();
			this.key = key;
			this.value = value;
		}

	}

	static class SoftNode<k, v> extends SoftReference<Node<k, v>> {
		public SoftNode(Node<k, v> referent, ReferenceQueue<Object> queue) {
			super(referent, queue);
		}
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
	public SoftNode<K, V> get(Object key) {
		checkValues();
		return super.get(key);
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
	public SoftNode<K, V> put(K key, SoftNode<K, V> value) {
		checkValues();
		return super.put(key, value);
	}

	public V putx(K key, V value) {
		checkValues();
		SoftNode<K, V> result = super.put(key, new SoftNode<K, V>(new Node<K, V>(key, value), queue));
		if (null != result)
			return result.get().getValue();
		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends SoftNode<K, V>> m) {
		checkValues();
		super.putAll(m);
	}

	@Override
	public SoftNode<K, V> remove(Object key) {
		checkValues();
		return super.remove(key);
	}

	@Override
	public void clear() {
		checkValues();
		super.clear();
	}

	@Override
	public java.util.concurrent.ConcurrentHashMap.KeySetView<K, SoftNode<K, V>> keySet() {
		checkValues();
		return super.keySet();
	}

	@Override
	public Collection<SoftNode<K, V>> values() {
		checkValues();
		return super.values();
	}

	@Override
	public Set<java.util.Map.Entry<K, SoftNode<K, V>>> entrySet() {
		checkValues();
		return super.entrySet();
	}

	@Override
	public int hashCode() {
		checkValues();
		return super.hashCode();
	}

	@Override
	public String toString() {
		checkValues();
		return super.toString();
	}

	@Override
	public boolean equals(Object o) {
		checkValues();
		return super.equals(o);
	}

	@Override
	public SoftNode<K, V> putIfAbsent(K key, SoftNode<K, V> value) {
		checkValues();
		return super.putIfAbsent(key, value);
	}

	@Override
	public boolean remove(Object key, Object value) {
		checkValues();
		return super.remove(key, value);
	}

	@Override
	public boolean replace(K key, SoftNode<K, V> oldValue, SoftNode<K, V> newValue) {
		checkValues();
		return super.replace(key, oldValue, newValue);
	}

	@Override
	public SoftNode<K, V> replace(K key, SoftNode<K, V> value) {
		checkValues();
		return super.replace(key, value);
	}

	@Override
	public SoftNode<K, V> getOrDefault(Object key, SoftNode<K, V> defaultValue) {
		checkValues();
		return super.getOrDefault(key, defaultValue);
	}

	@Override
	public void forEach(BiConsumer<? super K, ? super SoftNode<K, V>> action) {
		checkValues();
		super.forEach(action);
	}

	@Override
	public void replaceAll(BiFunction<? super K, ? super SoftNode<K, V>, ? extends SoftNode<K, V>> function) {
		checkValues();
		super.replaceAll(function);
	}

	@Override
	public SoftNode<K, V> computeIfAbsent(K key, Function<? super K, ? extends SoftNode<K, V>> mappingFunction) {
		checkValues();
		return super.computeIfAbsent(key, mappingFunction);
	}

	@Override
	public SoftNode<K, V> computeIfPresent(K key, BiFunction<? super K, ? super SoftNode<K, V>, ? extends SoftNode<K, V>> remappingFunction) {
		checkValues();
		return super.computeIfPresent(key, remappingFunction);
	}

	@Override
	public SoftNode<K, V> compute(K key, BiFunction<? super K, ? super SoftNode<K, V>, ? extends SoftNode<K, V>> remappingFunction) {
		checkValues();
		return super.compute(key, remappingFunction);
	}

	@Override
	public SoftNode<K, V> merge(K key, SoftNode<K, V> value, BiFunction<? super SoftNode<K, V>, ? super SoftNode<K, V>, ? extends SoftNode<K, V>> remappingFunction) {
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
	public Enumeration<SoftNode<K, V>> elements() {
		checkValues();
		return super.elements();
	}

	@Override
	public long mappingCount() {
		checkValues();
		return super.mappingCount();
	}

	@Override
	public java.util.concurrent.ConcurrentHashMap.KeySetView<K, SoftNode<K, V>> keySet(SoftNode<K, V> mappedValue) {
		checkValues();
		return super.keySet(mappedValue);
	}

	@Override
	public void forEach(long parallelismThreshold, BiConsumer<? super K, ? super SoftNode<K, V>> action) {
		checkValues();
		super.forEach(parallelismThreshold, action);
	}

	@Override
	public <U> void forEach(long parallelismThreshold, BiFunction<? super K, ? super SoftNode<K, V>, ? extends U> transformer, Consumer<? super U> action) {
		checkValues();
		super.forEach(parallelismThreshold, transformer, action);
	}

	@Override
	public <U> U search(long parallelismThreshold, BiFunction<? super K, ? super SoftNode<K, V>, ? extends U> searchFunction) {
		checkValues();
		return super.search(parallelismThreshold, searchFunction);
	}

	@Override
	public <U> U reduce(long parallelismThreshold, BiFunction<? super K, ? super SoftNode<K, V>, ? extends U> transformer, BiFunction<? super U, ? super U, ? extends U> reducer) {
		checkValues();
		return super.reduce(parallelismThreshold, transformer, reducer);
	}

	@Override
	public double reduceToDouble(long parallelismThreshold, ToDoubleBiFunction<? super K, ? super SoftNode<K, V>> transformer, double basis, DoubleBinaryOperator reducer) {
		checkValues();
		return super.reduceToDouble(parallelismThreshold, transformer, basis, reducer);
	}

	@Override
	public long reduceToLong(long parallelismThreshold, ToLongBiFunction<? super K, ? super SoftNode<K, V>> transformer, long basis, LongBinaryOperator reducer) {
		checkValues();
		return super.reduceToLong(parallelismThreshold, transformer, basis, reducer);
	}

	@Override
	public int reduceToInt(long parallelismThreshold, ToIntBiFunction<? super K, ? super SoftNode<K, V>> transformer, int basis, IntBinaryOperator reducer) {
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
	public void forEachValue(long parallelismThreshold, Consumer<? super SoftNode<K, V>> action) {
		checkValues();
		super.forEachValue(parallelismThreshold, action);
	}

	@Override
	public <U> void forEachValue(long parallelismThreshold, Function<? super SoftNode<K, V>, ? extends U> transformer, Consumer<? super U> action) {
		checkValues();
		super.forEachValue(parallelismThreshold, transformer, action);
	}

	@Override
	public <U> U searchValues(long parallelismThreshold, Function<? super SoftNode<K, V>, ? extends U> searchFunction) {
		checkValues();
		return super.searchValues(parallelismThreshold, searchFunction);
	}

	@Override
	public SoftNode<K, V> reduceValues(long parallelismThreshold, BiFunction<? super SoftNode<K, V>, ? super SoftNode<K, V>, ? extends SoftNode<K, V>> reducer) {
		checkValues();
		return super.reduceValues(parallelismThreshold, reducer);
	}

	@Override
	public <U> U reduceValues(long parallelismThreshold, Function<? super SoftNode<K, V>, ? extends U> transformer, BiFunction<? super U, ? super U, ? extends U> reducer) {
		checkValues();
		return super.reduceValues(parallelismThreshold, transformer, reducer);
	}

	@Override
	public double reduceValuesToDouble(long parallelismThreshold, ToDoubleFunction<? super SoftNode<K, V>> transformer, double basis, DoubleBinaryOperator reducer) {
		checkValues();
		return super.reduceValuesToDouble(parallelismThreshold, transformer, basis, reducer);
	}

	@Override
	public long reduceValuesToLong(long parallelismThreshold, ToLongFunction<? super SoftNode<K, V>> transformer, long basis, LongBinaryOperator reducer) {
		checkValues();
		return super.reduceValuesToLong(parallelismThreshold, transformer, basis, reducer);
	}

	@Override
	public int reduceValuesToInt(long parallelismThreshold, ToIntFunction<? super SoftNode<K, V>> transformer, int basis, IntBinaryOperator reducer) {
		checkValues();
		return super.reduceValuesToInt(parallelismThreshold, transformer, basis, reducer);
	}

	@Override
	public void forEachEntry(long parallelismThreshold, Consumer<? super java.util.Map.Entry<K, SoftNode<K, V>>> action) {
		checkValues();
		super.forEachEntry(parallelismThreshold, action);
	}

	@Override
	public <U> void forEachEntry(long parallelismThreshold, Function<java.util.Map.Entry<K, SoftNode<K, V>>, ? extends U> transformer, Consumer<? super U> action) {
		checkValues();
		super.forEachEntry(parallelismThreshold, transformer, action);
	}

	@Override
	public <U> U searchEntries(long parallelismThreshold, Function<java.util.Map.Entry<K, SoftNode<K, V>>, ? extends U> searchFunction) {
		checkValues();
		return super.searchEntries(parallelismThreshold, searchFunction);
	}

	@Override
	public java.util.Map.Entry<K, SoftNode<K, V>> reduceEntries(long parallelismThreshold, BiFunction<java.util.Map.Entry<K, SoftNode<K, V>>, java.util.Map.Entry<K, SoftNode<K, V>>, ? extends java.util.Map.Entry<K, SoftNode<K, V>>> reducer) {
		checkValues();
		return super.reduceEntries(parallelismThreshold, reducer);
	}

	@Override
	public <U> U reduceEntries(long parallelismThreshold, Function<java.util.Map.Entry<K, SoftNode<K, V>>, ? extends U> transformer, BiFunction<? super U, ? super U, ? extends U> reducer) {
		checkValues();
		return super.reduceEntries(parallelismThreshold, transformer, reducer);
	}

	@Override
	public double reduceEntriesToDouble(long parallelismThreshold, ToDoubleFunction<java.util.Map.Entry<K, SoftNode<K, V>>> transformer, double basis, DoubleBinaryOperator reducer) {
		checkValues();
		return super.reduceEntriesToDouble(parallelismThreshold, transformer, basis, reducer);
	}

	@Override
	public long reduceEntriesToLong(long parallelismThreshold, ToLongFunction<java.util.Map.Entry<K, SoftNode<K, V>>> transformer, long basis, LongBinaryOperator reducer) {
		checkValues();
		return super.reduceEntriesToLong(parallelismThreshold, transformer, basis, reducer);
	}

	@Override
	public int reduceEntriesToInt(long parallelismThreshold, ToIntFunction<java.util.Map.Entry<K, SoftNode<K, V>>> transformer, int basis, IntBinaryOperator reducer) {
		checkValues();
		return super.reduceEntriesToInt(parallelismThreshold, transformer, basis, reducer);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		checkValues();
		return super.clone();
	}

	@Override
	protected void finalize() throws Throwable {
		checkValues();
		super.finalize();
	}

}
