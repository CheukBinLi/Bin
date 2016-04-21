package com.cheuks.bin.anythingtest.jsontest;

public abstract class AbstractTestting {

	abstract void process();

	public void testting() {
		long start = System.currentTimeMillis();
		process();
		long end = System.currentTimeMillis();
		System.out.println((end - start) / 10);
	}

}
