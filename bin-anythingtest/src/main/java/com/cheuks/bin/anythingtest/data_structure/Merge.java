package com.cheuks.bin.anythingtest.data_structure;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/***
 * 归并
 * 
 * @author Ben-P
 *
 */
public class Merge {

	private static final SimpleDateFormat dataformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssss");

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//		List<Integer> list = Arrays.asList(1, 3, 5, 80, 22, 13, 44, 69, 52, 44, 99);
		List<Integer> list = new ArrayList<Integer>();
		Random r = new Random();
		int length = 100000;
		//		int length = 1000000;
		for (int i = 0; i < length; i++) {
			list.add((int) (r.nextInt(10000) + Math.random() * 100));
		}

		Date start = new Date();
		//		ExecutorService executorService = Executors.newCachedThreadPool();
		//		//单线程
		//		Future<List<Integer>> f = executorService.submit(new T(list));
		//		//		System.out.println(f.get());
		//		f.get();
		//		//		System.out.println("单线程冒泡时间：" + (new Date().getTime() - start.getTime()) / 60000);
		//		System.out.println("单线程冒泡时间：" + (new Date().getTime() - start.getTime()) / 1000);
		//		executorService.shutdown();
		//		//多线程
		start = new Date();
		//		System.err.println(new Merge().merge(list));
		new Merge().merge(list);
		//		System.out.println("多线程冒泡+归并时间：" + (new Date().getTime() - start.getTime()) / 60000);
		System.out.println("多线程冒泡+归并时间：" + (new Date().getTime() - start.getTime()) / 1000);
		start = new Date();
		new Merge().quickSort(list);
		System.out.println("快速时间：" + (new Date().getTime() - start.getTime()) / 1000);

	}

	static Semaphore semaphore = null;

	static class T implements Callable<List<Integer>> {

		private List<Integer> list;

		public T(List<Integer> list) {
			super();
			this.list = list;
		}

		int temp;

		public List<Integer> call() throws Exception {
			return bubbling(list);
		}

		//冒泡
		public List<Integer> bubbling(List<Integer> list) {
			try {
				for (int i = 0; i < list.size(); i++) {
					for (int j = i; j < list.size(); j++) {
						if (list.get(i) > list.get(j)) {
							temp = list.get(i);
							list.set(i, list.get(j));
							list.set(j, temp);
						}
					}
				}
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return list;
			} finally {
				if (null != semaphore)
					semaphore.release();
			}
		}
	}

	//快速
	public List<Integer> quickSort(List<Integer> list) throws InterruptedException, ExecutionException {
				int count = new Random().nextInt(list.size() - 1)/2;
//		int[] xCode = { list.get(0), list.get(list.size() / 2), list.get(list.size() - 1) };
		//		if (xCode[0] >= xCode[1] && xCode[0] <= xCode[2] || xCode[0] >= xCode[2] && xCode[0] <= xCode[1])
		//			if (xCode[1] >= xCode[0] && xCode[2] <= xCode[2] || xCode[1] >= xCode[2] && xCode[1] <= xCode[0])
		//				if (xCode[2] >= xCode[1] && xCode[2] <= xCode[0] || xCode[2] >= xCode[0] && xCode[2] <= xCode[1])
		//		int count = list.size() / 2;
//		int count = xCode[0] >= xCode[1] && xCode[0] <= xCode[2] || xCode[0] >= xCode[2] && xCode[0] <= xCode[1] ? xCode[0] : xCode[1] >= xCode[0] && xCode[2] <= xCode[2] || xCode[1] >= xCode[2] && xCode[1] <= xCode[0] ? xCode[1] : xCode[2] >= xCode[1] && xCode[2] <= xCode[0] || xCode[2] >= xCode[0] && xCode[2] <= xCode[1] ? xCode[2] : list.size() / 2;
		System.out.println(count);
		int codeNo = list.get(count);
		List<Integer> mix = new ArrayList<Integer>();
		List<Integer> max = new ArrayList<Integer>();
		for (int i = 0; i < list.size(); i++) {
			if (i == count)
				continue;
			if (list.get(i) > codeNo) {
				max.add(list.get(i));
			} else
				mix.add(list.get(i));
		}
		ExecutorService executorService = Executors.newCachedThreadPool();
		List<Future<List<Integer>>> futures = new ArrayList<Future<List<Integer>>>();
		futures.add(executorService.submit(new T(mix)));
		System.out.println("t1:" + mix.size());
		futures.add(executorService.submit(new T(max)));
		System.out.println("t2:" + max.size());
		list.clear();
		list.addAll(futures.get(0).get());//最小值
		list.add(codeNo);//中枢值
		list.addAll(futures.get(1).get());//最大值
		//		LinkedList<Integer> link1 = new LinkedList<Integer>(futures.get(0).get());
		//		LinkedList<Integer> link2 = new LinkedList<Integer>(futures.get(1).get());
		executorService.shutdown();
		//		List<Integer> result = new ArrayList<Integer>();
		//		for (int i = 0; i < list.size(); i++) {
		//			if (link1.size() == 0) {
		//				result.addAll(link2);
		//				break;
		//			} else if (link2.size() == 0) {
		//				result.addAll(link1);
		//				break;
		//			} else if (link1.peek() >= link2.peek()) {
		//				result.add(link2.remove());
		//			} else
		//				result.add(link1.remove());
		//		}
		//		return result;
		return list;
	}

	//归并
	public List<Integer> merge(List<Integer> list) throws InterruptedException, ExecutionException {

		//分线程分治
		int size = list.size();
		int proNo = Runtime.getRuntime().availableProcessors();
		semaphore = new Semaphore(size % Runtime.getRuntime().availableProcessors() != 0 ? proNo + 1 : proNo);
		ExecutorService executorService = Executors.newCachedThreadPool();
		List<Future<List<Integer>>> future = new ArrayList<Future<List<Integer>>>();
		for (int i = 0; i < proNo; i++) {
			future.add(executorService.submit(new T(list.subList(i * (size / proNo), i * (size / proNo) + (size / proNo)))));
		}
		future.add(executorService.submit(new T(list.subList(proNo * ((int) (size / proNo)), list.size()))));
		semaphore.acquire();//屏障
		executorService.shutdown();
		//分组结果
		int countSize = 0;
		List<LinkedList<Integer>> linked = new ArrayList<LinkedList<Integer>>();
		for (int i = 0; i < future.size(); i++) {
			LinkedList<Integer> link = new LinkedList(future.get(i).get());
			linked.add(link);
			//			System.out.println(linked.get(i));
			countSize += link.size();
		}
		//合并
		List<Integer> x = new ArrayList<Integer>();
		int way = 0;//链表序列号(remove)
		int biter = 0;//大值
		boolean write = true;//合并比对开始
		for (int i = 0; i < countSize; i++) {//总数插入
			for (int j = 0; j < linked.size(); j++) {//合并前比对
				if (linked.get(j).size() < 1)
					continue;
				if (write) {
					biter = linked.get(j).peek();
					way = j;
					write = false;
				} else {
					if (biter > linked.get(j).peek()) {
						biter = linked.get(j).peek();
						way = j;
					}
				}
			}
			write = true;//重新比对一下对开关
			x.add(biter);//复制最大/最小
			linked.get(way).remove();//移最大或最小
		}
		return x;
	}
}
