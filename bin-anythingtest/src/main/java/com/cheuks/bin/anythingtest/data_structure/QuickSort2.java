package com.cheuks.bin.anythingtest.data_structure;


public class QuickSort2 {

	public static void main(String[] args) {
		int[] a = { 91, 2, 3, 14, 6, 7, 9, 0, 12, 332, 245, 1 };
		for (int i : a)
			System.out.print(i + " ");
		System.out.println();
		// partition(a, 0, a.length - 1);
		// s3(a, 0, a.length - 1);
		s4(a, 0, a.length - 1);
		for (int i : a)
			System.err.print(i + " ");
		System.err.println();
	}

	public static void sort(int[] a, int left, int right) {
	}

	public static void s4(int[] a, int left, int right) {
		if (left < right) {
			int key = a[left];
			int low = left;
			int height = right;
			while (low < height) {
				while (low < height && a[height] > key) {
					height--;
				}
				a[low] = a[height];
				// a[height] = key;
				while (low < height && a[low] < key) {
					low++;
				}
				a[height] = a[low];
			}
			a[low] = key;
			s4(a, left, low - 1);
			s4(a, low + 1, right);
		}
	}

	public static void s3(int[] a, int left, int right) {
		int x = left;
		int hi = right;
		int temp = 0;
		if (x == hi) {
			return;
		}
		while (x != hi) {
			System.out.println(x + ":" + hi);
			if (a[x] > a[hi]) {
				temp = a[hi];
				a[hi] = a[x];
				a[x] = temp;
			}
			// hi--;
			x++;
		}
		// 排序第一次完成
		for (int i : a)
			System.err.print(i + " ");
		System.err.println();
		// lo--;
		// hi++;
		// quickSort(a, lo0, lo);
		// quickSort(a, hi, hi0);
		s3(a, left, --x);
		// s3(a, ++right, hi);
	}

	public static void partition(int[] a, int left, int right) {
		System.out.println(a[left]);
		int lo = left;
		int hi = right;
		int temp = a[lo];
		while (lo != hi) {
			while (lo != hi && a[left] > a[lo++]) {// 找比X小的值，交换
				break;
			}
			temp = a[left];
			a[left] = a[lo];// 交换
			a[lo] = temp;
			for (int i : a)
				System.err.print(i + " ");
			System.out.println();
			while (lo != hi && a[lo] < a[hi]) {// 找比X大
				hi--;
				break;
			}
			temp = a[hi];
			a[hi] = a[lo];
			a[lo] = temp;
			left = hi;
			for (int i : a)
				System.out.print(i + " ");
			System.out.println();
		}
	}

	public static void quickSort(int[] a, int lo0, int hi0) {
		int lo = lo0;
		int hi = hi0;

		if (lo >= hi)
			return;

		// 确定指针方向的逻辑变量
		boolean transfer = true;
		while (lo != hi) {
			if (a[lo] > a[hi]) {
				// 交换数字
				int temp = a[lo];
				a[lo] = a[hi];
				a[hi] = temp;
				// 决定下标移动，还是上标移动
				transfer = (transfer == true) ? false : true;
			}

			// 将指针向前或者向后移动
			if (transfer)
				hi--;
			else
				lo++;

			// 显示每一次指针移动的数组数字的变化
			/*
			 * for(int i = 0; i < a.length; ++i) { System.out.print(a[i] + ","); } System.out.print(" (lo,hi) = " + "(" + lo + "," + hi + ")"); System.out.println("");
			 */
		}
		// 将数组分开两半，确定每个数字的正确位置
		lo--;
		hi++;
		quickSort(a, lo0, lo);
		quickSort(a, hi, hi0);
	}

}
