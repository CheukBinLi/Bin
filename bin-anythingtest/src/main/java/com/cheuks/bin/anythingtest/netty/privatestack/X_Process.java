package com.cheuks.bin.anythingtest.netty.privatestack;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class X_Process {

	private static Set<Process> a = new HashSet<Process>();

	public static void main(String[] args) throws IOException {
		System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
		if (true)
			return;
		Runtime runtime = Runtime.getRuntime();
		for (int i = 0; i < 16; i++) {
			a.add(runtime.exec("java -jar " + Thread.currentThread().getContextClassLoader().getResource("") + "client.jar"));
		}
	}

}
