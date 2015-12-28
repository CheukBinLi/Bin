package com.cheuks.bin.net.server.handler.test;

public class A implements Cloneable {

	private int a = 10;

	private void ss() {
		System.out.println("A" + a);
	}

	public static void main(String[] args) throws CloneNotSupportedException {
		A a1 = new A();
		A a2 = (A) a1.clone();
		a2.a = 199;
		a1.ss();
		a2.ss();
	}

}
