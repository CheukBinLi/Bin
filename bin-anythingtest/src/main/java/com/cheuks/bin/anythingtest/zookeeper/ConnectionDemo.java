package com.cheuks.bin.anythingtest.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.AsyncCallback.Create2Callback;
import org.apache.zookeeper.AsyncCallback.DataCallback;

public class ConnectionDemo {

	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		final CountDownLatch countDownLatch1 = new CountDownLatch(2);
		final CountDownLatch countDownLatch2 = new CountDownLatch(2);

		ZooKeeper zk = new ZooKeeper("192.168.168.150:2181,192.168.168.119:2181,192.168.168.124:2181", 2000, new Watcher() {
			public void process(WatchedEvent arg0) {
				try {
					System.out.println(arg0);
				} finally {
					countDownLatch.countDown();
				}
			}
		});
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(zk.getState());
		//同步节点
		String result = null;
		try {
			result = zk.create("/java_path", "default".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		} catch (KeeperException e) {
			//						e.printStackTrace();
		}
		System.out.println(result);

		//异步节点

		zk.create("/java_a_path", "异步节点".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new StringCallback() {
			public void processResult(int arg0, String arg1, Object arg2, String arg3) {
				System.out.println("0:" + arg0);
				System.out.println("1:" + arg1);
				System.out.println("2:" + arg2);
				System.out.println("3:" + arg3);
				countDownLatch1.countDown();
			}
		}, "this is context!");

		zk.create("/java_b_path", "异步节点".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new Create2Callback() {

			public void processResult(int arg0, String arg1, Object arg2, String arg3, Stat arg4) {
				System.out.println("0:" + arg0);
				System.out.println("1:" + arg1);
				System.out.println("2:" + arg2);
				System.out.println("3:" + arg3);
				System.out.println("4:" + arg4);
				countDownLatch1.countDown();
			}
		}, "context");
		countDownLatch1.await();

		//读数据

		Stat stat = new Stat();
		System.out.println(new String(zk.getData("/java_path", true, stat)));
		System.err.println(stat.getMtime());

		System.out.println(new String(zk.getData("/java_path", false, stat)));

		System.out.println(new String(zk.getData("/java_path", new Watcher() {

			public void process(WatchedEvent arg0) {
				System.out.println("watchEven:" + arg0);
				countDownLatch2.countDown();
			}
		}, stat)));

		//导步

		zk.getData("/java_a_path", true, new DataCallback() {
			public void processResult(int arg0, String arg1, Object arg2, byte[] arg3, Stat arg4) {
				System.err.println("0:" + arg0);
				System.err.println("1:" + arg1);
				System.err.println("2:" + arg2);
				System.err.println("3:" + new String(arg3));
				System.err.println("4:" + arg4);
				countDownLatch2.countDown();
			}
		}, "context");
		zk.getData("/java_a_path", new Watcher() {

			public void process(WatchedEvent arg0) {
				System.err.println("watchEven:" + arg0);
			}

		}, new DataCallback() {
			public void processResult(int arg0, String arg1, Object arg2, byte[] arg3, Stat arg4) {
				System.err.println("0:" + arg0);
				System.err.println("1:" + arg1);
				System.err.println("2:" + arg2);
				System.err.println("3:" + new String(arg3));
				System.err.println("4:" + arg4);
			}
		}, "context");
		countDownLatch2.await();
	}
}
