package com.cheuks.bin.anythingtest.data_structure;

import java.util.Arrays;

public class RecursiveSort {

	public int[] sort(int[] o, int current, int next, boolean acd) {
		if (next < o.length) {
			if ((o[current] > o[next] && acd) || (o[current] < o[next] && !acd)) {
				o[current] = o[current] + o[next];
				o[next] = o[current] - o[next];
				o[current] = o[current] - o[next];
			}
			o = sort(o, current, ++next, acd);
		}
		else if (current + 1 < o.length)
			o = sort(o, ++current, 0, acd);
		return o;
	}

	public static void main(String[] args) {
		int[] a = { 3, 9, 10, 5, 2, 7, 4, 6 };
		a = new RecursiveSort().sort(a, 0, 0, true);
		System.out.println(Arrays.toString(a));
	}

}
