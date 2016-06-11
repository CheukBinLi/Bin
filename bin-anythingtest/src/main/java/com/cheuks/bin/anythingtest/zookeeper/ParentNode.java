package com.cheuks.bin.anythingtest.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooDefs.Ids;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ParentNode {

	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		final CountDownLatch countDownLatch1 = new CountDownLatch(1);
		ZooKeeper zk = new ZooKeeper("192.168.168.150:2181,192.168.168.119:2181,192.168.168.124:2181", 2000, new Watcher() {
			public void process(WatchedEvent arg0) {
				try {
					System.out.println("连接侦听：" + arg0);
				} finally {
					countDownLatch.countDown();
				}
			}
		});
		countDownLatch.await();
		try {
			zk.delete("/java/acl/a", 0);
			zk.delete("/java/acl", 0);
			zk.delete("/java", 0);
		} catch (Exception e) {
		}
		zk.create("/java", null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.create("/java/acl", null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.create("/java/acl/a", null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.create("/temp", null, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		List<String> childrens = zk.getChildren("/java", true);
		for (String s : childrens) {
			System.out.println(s);
			childrens = zk.getChildren("/java/" + s, true);
			for (String s1 : childrens)
				System.out.println(s1);
		}
		countDownLatch1.await();
	}

}
