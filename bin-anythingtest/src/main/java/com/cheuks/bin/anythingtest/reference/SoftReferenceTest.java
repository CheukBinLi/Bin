package com.cheuks.bin.anythingtest.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Map.Entry;
import java.util.Random;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import com.cheuks.bin.anythingtest.reference.SoftMap.valueX;

public class SoftReferenceTest {

	public void x() {
		WeakReference<Object> a1;
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

	public void x2() {
		Thread T = new Thread(new Runnable() {

			public void run() {
				try {
					Thread.sleep(20000);
					synchronized (SoftReferenceTest.class) {
						SoftReferenceTest.class.notify();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		T.start();
		ConcurrentHashMap<String, WeakReference<String>> soft = new ConcurrentHashMap<String, WeakReference<String>>();
		WeakHashMap<String, String> weakHashMap = new WeakHashMap<String, String>();
		String[] a = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11" };
		for (String s : a)
			soft.put(s, new WeakReference<String>(s));
		for (String s : a)
			weakHashMap.put(s, s);
		for (Entry<String, WeakReference<String>> en : soft.entrySet())
			System.out.print(en.getValue().get());
		System.out.println();
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		for (int i = 0; i < 100000; i++) {
			for (int j = 0; j < 10; j++) {
				sb.append((char) (r.nextInt(5000) + 1));
			}
			soft.put(Integer.toString(i), new WeakReference<String>(sb.toString()));
			if (i % 20 == 0)
				System.out.println(Runtime.getRuntime().totalMemory() + "   " + Runtime.getRuntime().freeMemory());
		}
		System.err.println(Runtime.getRuntime().totalMemory() + "   " + Runtime.getRuntime().freeMemory());
		// System.gc();
		// System.gc();
		// synchronized (SoftReferenceTest.class) {
		// try {
		// SoftReferenceTest.class.wait();
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }
		// System.gc();
		// System.gc();
		// System.gc();
		// System.gc();
		// System.gc();
		// for (Entry<String, WeakReference<String>> en : soft.entrySet())
		// System.err.print(en.getValue().get());
		// System.err.println();
		// for (Entry<String, String> en : weakHashMap.entrySet())
		// System.out.print(en.getKey());
		// System.err.println(Runtime.getRuntime().totalMemory() + " " + Runtime.getRuntime().freeMemory());
		int nullCount = 0;
		int count = 0;
		for (Entry<String, WeakReference<String>> en : soft.entrySet()) {
			if (null == en.getValue().get())
				nullCount++;
			count++;
		}
		System.err.print("总数：" + count + " null数量" + nullCount);
	}

	public void x3() {
		SoftMap<String, String> soft = new SoftMap<String, String>();
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		for (int i = 0; i < 1000000; i++) {
			for (int j = 0; j < 10; j++) {
				sb.append((char) (r.nextInt(5000) + 1));
			}
			soft.put(Integer.toString(i), sb.toString());
			if (i % 20 == 0)
				System.out.println(Runtime.getRuntime().totalMemory() + "   " + Runtime.getRuntime().freeMemory());
		}
		System.err.println(Runtime.getRuntime().totalMemory() + "   " + Runtime.getRuntime().freeMemory());
		int nullCount = 0;
		int count = 0;
		for (Entry<String, valueX<String>> en : soft.entrySet()) {
			if (null == en.getValue().getValue())
				nullCount++;
			count++;
		}
		System.err.print("总数：" + count + " null数量" + nullCount);
	}

	public void x4() {
		// ConcurrentHashMap<String, WeakReference<String>> soft = new ConcurrentHashMap<String, WeakReference<String>>();
		WeakHashMap<String, String> weakHashMap = new WeakHashMap<String, String>();
		String[] a = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11" };
		for (String s : a)
			weakHashMap.put(s, s);
		for (String s : a)
			weakHashMap.put(s, s);
		for (Entry<String, String> en : weakHashMap.entrySet())
			System.out.print(en.getValue());
		System.out.println();
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		for (int i = 0; i < 100000; i++) {
			for (int j = 0; j < 10; j++) {
				sb.append((char) (r.nextInt(5000) + 1));
			}
			weakHashMap.put(Integer.toString(i), sb.toString());
			if (i % 20 == 0)
				System.out.println(Runtime.getRuntime().totalMemory() + "   " + Runtime.getRuntime().freeMemory());
			if (i > 20000)
				this.toString();
		}
		System.err.println(Runtime.getRuntime().totalMemory() + "   " + Runtime.getRuntime().freeMemory());
		int nullCount = 0;
		int count = 0;
		for (Entry<String, String> en : weakHashMap.entrySet()) {
			if (null == en.getValue())
				nullCount++;
			count++;
		}
		System.err.print("总数：" + count + " null数量" + nullCount);
	}

	public void x5() {
		T1<String, String> weakHashMap = new T1<String, String>();
		String[] a = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11" };
		for (String s : a)
			weakHashMap.put(s, s);
		for (String s : a)
			weakHashMap.put(s, s);
		for (Entry<String, Object> en : weakHashMap.entrySet())
			System.out.print(en.getValue());
		System.out.println();
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		for (int i = 0; i < 100000; i++) {
			for (int j = 0; j < 10; j++) {
				sb.append((char) (r.nextInt(5000) + 1));
			}
			weakHashMap.put(Integer.toString(i), sb.toString());
			if (i % 20 == 0)
				System.out.println(Runtime.getRuntime().totalMemory() + "   " + Runtime.getRuntime().freeMemory());
			if (i > 20000)
				this.toString();
		}
		System.err.println(Runtime.getRuntime().totalMemory() + "   " + Runtime.getRuntime().freeMemory());
		int nullCount = 0;
		int count = 0;
		// for (Entry<String, String> en : weakHashMap.entrySet()) {
		// if (null == en.getValue())
		// nullCount++;
		// count++;
		// }
		System.err.print("总数：" + count + " null数量" + nullCount);
	}

	public void x6() {
		ReferenceQueue<String> queue = new ReferenceQueue<String>();
		ConcurrentHashMap<String, SoftReference<String>> weakHashMap = new ConcurrentHashMap<String, SoftReference<String>>();
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		Object key;
		for (int i = 0; i < 100000; i++) {
			for (int j = 0; j < 10; j++) {
				sb.append((char) (r.nextInt(5000) + 1));
			}
			weakHashMap.put(Integer.toString(i), new SoftReference<String>(sb.toString(), queue));
			if (i % 20 == 0)
				System.out.println(Runtime.getRuntime().totalMemory() + "   " + Runtime.getRuntime().freeMemory());
			if (i > 20000)
				this.toString();
			if (null != (key = queue.poll())) {
				System.err.println(((SoftReference<String>) key).get());
				System.err.print("");
			}
		}
		System.err.println(Runtime.getRuntime().totalMemory() + "   " + Runtime.getRuntime().freeMemory());
		int nullCount = 0;
		int count = 0;
		// for (Entry<String, String> en : weakHashMap.entrySet()) {
		// if (null == en.getValue())
		// nullCount++;
		// count++;
		// }
		System.err.print("总数：" + count + " null数量" + nullCount);
	}

	public void x7() {
		WeakHashMap<String, String> weakHashMap = new WeakHashMap<String, String>();
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		for (int i = 0; i < 100000; i++) {
			for (int j = 0; j < 10; j++) {
				sb.append((char) (r.nextInt(5000) + 1));
			}
			weakHashMap.put(Integer.toString(i), sb.toString());
			if (i % 20 == 0)
				System.out.println(Runtime.getRuntime().totalMemory() + "   " + Runtime.getRuntime().freeMemory());
			if (i > 20000)
				this.toString();
		}
		System.err.println(Runtime.getRuntime().totalMemory() + "   " + Runtime.getRuntime().freeMemory());
		int nullCount = 0;
		int count = 0;
		// for (Entry<String, String> en : weakHashMap.entrySet()) {
		// if (null == en.getValue())
		// nullCount++;
		// count++;
		// }
		System.err.print("总数：" + count + " null数量" + nullCount);
	}

	public static void main(String[] args) {
		new SoftReferenceTest().x7();
		// sun.misc.Unsafe u = sun.misc.Unsafe.getUnsafe();
	}

}
