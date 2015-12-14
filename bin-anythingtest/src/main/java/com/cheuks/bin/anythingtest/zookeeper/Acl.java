package com.cheuks.bin.anythingtest.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/***
 * 权限*
 * 
 * Copyright 2015    CHEUK.BIN.LI Individual All
 *  
 * ALL RIGHT RESERVED
 *  
 * CREATE ON 2015年12月10日下午3:20:21
 *  
 * EMAIL:20796698@QQ.COM
 *  
 * GITHUB:https://github.com/fdisk123
 * 
 * @author CHEUK.BIN.LI
 * 
 * @see 
 *
 */
public class Acl {

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

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
		//权限
		zk.addAuthInfo("digest", "你好吗！".getBytes());
		try {
			zk.delete("/java_acl_path", 0);
		} catch (Exception e) {
			//			e.printStackTrace();
		}

		zk.create("/java_acl_path", "权限检测".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);

		ZooKeeper zk1 = new ZooKeeper("192.168.168.150:2181,192.168.168.119:2181,192.168.168.124:2181", 2000, new Watcher() {
			public void process(WatchedEvent arg0) {
				try {
					System.out.println("连接1侦听：" + arg0);
				} finally {
					countDownLatch1.countDown();
				}
			}
		});
		countDownLatch1.await();
		//		zk1.addAuthInfo("digest", "你好吗".getBytes());
		zk1.addAuthInfo("digest", "你好吗！".getBytes());
		System.err.println(new String(zk1.getData("/java_acl_path", true, new Stat())));
	}

}
