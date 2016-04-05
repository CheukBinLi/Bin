package com.cheuks.bin.anythingtest.netty.privatestack;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class X_Client {

	public static void main(String[] args) throws InterruptedException {

		final CountDownLatch count = new CountDownLatch(1);
		ExecutorService executorService = Executors.newCachedThreadPool();
		Set<Future<Void>> future = new HashSet<Future<Void>>();
		for (int i = 0; i < 500; i++)
			future.add((Future) executorService.submit(new Callable<Void>() {
				public Void call() throws Exception {
					new client().connection(new InetSocketAddress("192.168.168.43", 1191));
					return null;
				}
			}));
		System.out.println("等待");
		count.await();
	}

}
