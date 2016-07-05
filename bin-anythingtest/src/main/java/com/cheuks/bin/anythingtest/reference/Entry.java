package com.cheuks.bin.anythingtest.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Map;

public class Entry<K, V> extends SoftReference<K> implements Map.Entry<K, V> {

	final int hash;
	final K key;
	volatile V val;
	volatile Entry<K, V> next;

	Entry(int hash, K key, V val, ReferenceQueue<? super K> queue, Entry<K, V> next) {
		super(key, queue);
		this.hash = hash;
		this.key = key;
		this.val = val;
		this.next = next;
	}

	public final K getKey() {
		return key;
	}

	public final V getValue() {
		return val;
	}

	public final int hashCode() {
		return key.hashCode() ^ val.hashCode();
	}

	public final String toString() {
		return key + "=" + val;
	}

	public final V setValue(V value) {
		throw new UnsupportedOperationException();
	}

	public final boolean equals(Object o) {
		Object k, v, u;
		Map.Entry<?, ?> e;
		return ((o instanceof Map.Entry) && (k = (e = (Map.Entry<?, ?>) o).getKey()) != null && (v = e.getValue()) != null && (k == key || k.equals(key)) && (v == (u = val) || v.equals(u)));
	}

	Entry<K, V> find(int h, Object k) {
		Entry<K, V> e = this;
		if (k != null) {
			do {
				K ek;
				if (e.hash == h && ((ek = e.key) == k || (ek != null && k.equals(ek))))
					return e;
			} while ((e = e.next) != null);
		}
		return null;
	}

}
