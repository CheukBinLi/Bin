package com.cheuks.bin.anythingtest.data_structure;

public class Recursion0 {

	public static int getNo(int month) {
		int t1, t2;// 新/旧兔仔
		if (month == 1 || month == 2)
			return 1;
		else {
			t1 = getNo(month - 1);// 当前兔仔
			t2 = getNo(month - 2);// 新生的兔仔
			return t1 + t2;
		}
	}

	public static void main(String[] args) {
		System.out.println(getNo(12));
	}
}
