package com.cheuks.bin.anythingtest.data_structure;

public class InsertSort {

	public static void main(String[] args) {
		int a[] = insertSort(new int[] { 21, 14, 44, 1, 4, 5, 2, 3, 8, 22, 99, 8 });
		for (int i : a)
			System.out.print(i + " ");
		System.out.println();
	}

	public static int[] insertSort(int[] a) {
		int node;
		int j;
		for (int i = 1; i < a.length; i++) {
			j = i - 1;
			node = a[i];
			while (j >= 0 && node < a[j]) {
				// 向左移动
				a[j + 1] = a[j];
				j--;
			}
			// 最后一位
			a[j + 1] = node;
		}

		return a;
	}
}
