package com.cheuks.bin.anythingtest.data_structure;

public class QuickSort {

	public static void main(String[] args) {
		int[] a = { 5, 2, 3, 1, 6, 7, 9, 0, 12, 332, 245, 1 };
		// int a1 = partition(a, 0, a.length - 1);
		// System.out.println(a1);
		// a1 = partition(a, 0, a.length - 1);
		// System.out.println(a1);
		sort(a, 0, a.length - 1);
		for (int i : a)
			System.out.print(i + " ");
	}

	public static void sort(int[] a, int left, int right) {
		if (left < right) {
			int x = partition(a, 0, a.length - 1);
			sort(a, left, x - 1);
			sort(a, x + 1, right);
		}
	}

	public static int partition(int[] a, int left, int right) {
		int hub = a[left];
		// System.out.println(hub);
		while (left < right) {
			while (left < right && a[right] >= hub) {// 高位向低位遍历,遇到小于枢纽退出移动元素
				System.err.println(right + ":" + a[right]);
				--right;
			}
			a[left] = a[right];
			while (left < right && a[left] <= hub) {// 低位向高位遍历,遇到大于枢纽退出移动元素
				++left;
			}
			a[right] = a[left];
		}
		a[left] = hub;
		return left;
	}
}
