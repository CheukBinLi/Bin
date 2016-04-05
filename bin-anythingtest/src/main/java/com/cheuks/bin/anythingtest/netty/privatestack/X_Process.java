package com.cheuks.bin.anythingtest.netty.privatestack;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class X_Process {

	private static Set<Process> a = new HashSet<Process>();

	public static void main(String[] args) throws IOException, InterruptedException {
		CountDownLatch count = new CountDownLatch(1);
		System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath());
		//				if (true)
		//					return;
		Runtime runtime = Runtime.getRuntime();
		for (int i = 0; i < 16; i++) {
			a.add(runtime.exec("java -jar " + Thread.currentThread().getContextClassLoader().getResource("").getPath() + "client.jar"));
		}
		count.await();
	}

}
