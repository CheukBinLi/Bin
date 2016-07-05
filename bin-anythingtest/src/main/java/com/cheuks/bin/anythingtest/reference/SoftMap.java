package com.cheuks.bin.anythingtest.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SoftMap<K, V> {

//	private ReferenceQueue<Object> queue = new ReferenceQueue<Object>();
	private ConcurrentHashMap<K, valueX<K>> data = new ConcurrentHashMap<K, valueX<K>>();

	public V put(K k, V v) {
		valueX<K> result = data.put(k, new valueX<K>(k, null, v));
		if (null != result)
			result.getValue();
		return null;
	}

	public Set<java.util.Map.Entry<K, valueX<K>>> entrySet() {
		return data.entrySet();
	}

	static class valueX<K> extends SoftReference<K> {

		private Object value;

		public valueX(K key, ReferenceQueue<? super K> queue, Object value) {
			super(key, queue);
			this.value = value;
		}

		public <V> V getValue() {
			if (null != value)
				return (V) value;
			return null;
		}

		public valueX<?> setValue(Object value) {
			this.value = value;
			return this;
		}
	}
}
