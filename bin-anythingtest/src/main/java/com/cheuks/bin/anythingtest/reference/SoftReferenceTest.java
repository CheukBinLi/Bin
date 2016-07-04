package com.cheuks.bin.anythingtest.reference;

import java.lang.ref.SoftReference;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class SoftReferenceTest {

	public void x() {

		ConcurrentHashMap<String, SoftReference<String>> soft = new ConcurrentHashMap<String, SoftReference<String>>();
		String[] a = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11" };
		for (String s : a)
			soft.put(s, new SoftReference<String>(s));
		for (Entry<String, SoftReference<String>> en : soft.entrySet())
			System.err.println(en.getValue().get());
		System.gc();
		System.gc();
		System.gc();
		for (Entry<String, SoftReference<String>> en : soft.entrySet())
			System.err.println(en.getValue().get());
	}

	public static void main(String[] args) {
		new SoftReferenceTest().x();
	}

}
