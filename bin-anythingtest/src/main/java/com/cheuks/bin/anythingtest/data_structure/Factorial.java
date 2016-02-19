package com.cheuks.bin.anythingtest.data_structure;

/***
 * 阶乘
 * @author hnbh
 *
 */
public class Factorial {

	public static int factorial(int n) {
		if (n <= 1)
			return 1;
		return n * factorial(n - 1);
	}

	public static void main(String[] args) {
		System.err.println(factorial(12));
	}

}
