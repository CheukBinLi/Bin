package com.cheuks.bin.anythingtest.data_structure;

public class X {

	public static void main(String[] args) {
		int[] i = getCR(35, 94);
		System.out.println("鸡:" + i[0] + " 兔:" + i[1]);
	}

	public static int[] getCR(int head, int foot) {
		int c = 0;
		int r = 0;
		for (int i = 0; i < head; i++) {
			r = head - i;
			if (i * 2 + r * 4 == foot) {
				c = i;
				break;
			}
		}
		return new int[] { c, r };
	}
}
