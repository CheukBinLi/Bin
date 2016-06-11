package com.cheuks.bin.anythingtest.zookeeper.watch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class WatcthDemo implements Watch {

	/***
	 * 权限
	 */
	@Test
	public void Acl() {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		final CountDownLatch countDownLatch1 = new CountDownLatch(1);
		final CountDownLatch countDownLatch2 = new CountDownLatch(1);
		try {
			ZooKeeper zkClient = new ZooKeeper(ipAddress, 5000, new AclWatch("正确ACL", countDownLatch1));
			countDownLatch1.await();
			zkClient.addAuthInfo("digest", "keeper:true".getBytes());
			Stat stat;
			if (null != (stat = zkClient.exists("/zk-book", true))) {
				zkClient.delete("/zk-book", stat.getVersion());
			}
			zkClient.create("/zk-book", "".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);

			ZooKeeper error = new ZooKeeper(ipAddress, 5000, new AclWatch("错误ACL", countDownLatch2));
			countDownLatch2.await();
			error.addAuthInfo("digest", "keeper:error".getBytes());
			error.getData("/zk-book", true, null);
			countDownLatch.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class AclWatch implements Watcher {

		private String name;
		private CountDownLatch countDownLatch;

		public void process(WatchedEvent event) {
			try {
				System.err.println(name + ":" + event.getType());
			} finally {
				countDownLatch.countDown();
			}
		}

		public AclWatch(String name, CountDownLatch countDownLatch) {
			super();
			this.name = name;
			this.countDownLatch = countDownLatch;
		}
	}

}
