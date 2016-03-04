package com.cheuks.bin.anythingtest.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalDemo {

	private static ThreadLocal<Integer> local = new ThreadLocal<Integer>();

	public static void main(String[] args) {
		final ThreadLocalDemo threadLocalDemo = new ThreadLocalDemo();
		ExecutorService executorService = Executors.newCachedThreadPool();
		int i = 10;
		while (--i > 0)
			executorService.execute(new Runnable() {

				public void run() {
					System.out.println(threadLocalDemo.getAndAdd());
				}
			});

	}

	int get() {
		return local.get();
	}

	int getAndAdd() {
		if (null == local.get()) {
			local.set(0);
			return 0;
		}
		else
			local.set(local.get() + 1);
		return local.get();
	}

}
