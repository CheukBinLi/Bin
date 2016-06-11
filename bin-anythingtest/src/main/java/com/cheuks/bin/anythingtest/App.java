package com.cheuks.bin.anythingtest;

import java.util.StringTokenizer;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		try {
			System.out.println("Hello World!");
			Object o = new App().a("你好吗？", 2);
			System.out.println(o);

			String A = "adlsfkjsdklfnsadfbjkxnewfsfh";
			StringTokenizer st = new StringTokenizer(A, "f");
			String[] xx = new String[st.countTokens()];
			int i = 0;
			while (st.hasMoreTokens()) {
				xx[i++] = st.nextToken();
			}
			throw new B();
		} catch (A e) {
			System.out.println("A");
		} catch (Exception e) {
			System.out.println("super");
		}

	}

	public String[] a(String str, int x) {
		return str.split(str.substring(x - 1, x));
	}

}

class A extends Exception {
}

class B extends A {
}