package com.cheuks.bin.anythingtest.data_structure;

import java.util.LinkedList;
import java.util.List;

/***
 * 二叉堆
 * @author hnbh
 *
 */
public class TwoForkHeap {

	public static void main(String[] args) {

		List<Integer> heap = new LinkedList<Integer>();

		int x = 10;
//		while (--x > 0) {
//			heap.add(x * 10);
//		}
		for (int c : heap)
			System.out.print(c + " ");

		// heap.add(61);
		// int currentSize = heap.size() - 1;
		// System.out.println();
		// System.out.println(heap.get(currentSize));
		// System.out.println(heap.get(currentSize/2));
		// System.out.println(heap.get(currentSize).compareTo(heap.get(currentSize / 2)) < 0);
		// 不停除2向上爬
		// for (; heap.get(currentSize).compareTo(heap.get(currentSize / 2)) > 0; currentSize /= 2) {
		// heap.set(currentSize, heap.get(currentSize / 2));
		// }
		System.out.println();
		heap = inster(heap, 100);
		heap = inster(heap, 40);
		heap = inster(heap, 90);
		heap = inster(heap, 30);
		heap = inster(heap, 20);
		heap = inster(heap, 10);
		heap = inster(heap, 30);
		heap = inster(heap, 60);
		heap = inster(heap, 80);
		heap = inster(heap, 87);
		for (int c : heap)
			System.out.print(c + " ");
	}

	public static List<Integer> inster(List<Integer> list, Integer obj) {
		// for (; heap.get(currentSize).compareTo(heap.get(currentSize / 2)) > 0; currentSize /= 2) {
		// heap.set(currentSize, heap.get(currentSize / 2));
		// }
		int listSize = list.size();
		list.add(obj);
		// 不停除2向上爬
		for (; obj.compareTo(list.get(listSize / 2)) > 0; listSize /= 2) {
			// listSize不停队2,高低位交换
			System.out.println(listSize);
			list.set(listSize, list.get(listSize / 2));
			if (listSize == 0)
				break;
		}
		// 爬到最高位
		list.set(listSize, obj);
		for (int c : list)
			System.err.print(c + " ");
		System.err.println();
		return list;
	}
}
