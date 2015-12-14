package com.cheuks.bin.anythingtest.zookeeper.watch;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class Watcher_Demo_0 {

	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		final ZooKeeperEX zkClient = new ZooKeeperEX(ServerList.getList(), 5000, null);
		zkClient.setWatcher(new WatcherEx(zkClient));
		zkClient.exists("/listening_path", true);
		countDownLatch.await();
	}

	static class WatcherEx implements Watcher {

		private ZooKeeper zk;

		public void process(WatchedEvent event) {
			// System.err.println(String.format("#%s", name));
			System.err.println(String.format("----------%s%s", "Path:", event.getPath()));
			// System.err.println(String.format("----------%s%s", "State:",
			// event.getState()));
			System.err.println(String.format("----------%s%s", "Type:", event.getType()));
			// System.err.println(String.format("----------%s%s", "Wrapper:",
			// event.getWrapper()));
			try {
				if (null != event.getPath())
					zk.exists(event.getPath(), true);
				zk.getChildren(event.getPath(), new childrenWat1cher(zk));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public WatcherEx(ZooKeeper zk) {
			super();
			this.zk = zk;
		}

	}

	static class childrenWat1cher implements Watcher {

		private ZooKeeper zk;

		public void process(WatchedEvent event) {
			System.err.println(String.format("----------%s%s", "Path:", event.getPath()));
			// System.err.println(String.format("----------%s%s", "State:",
			// event.getState()));
			System.err.println(String.format("----------%s%s", "Type:", event.getType()));
			// System.err.println(String.format("----------%s%s", "Wrapper:",
			// event.getWrapper()));
			try {
				zk.exists(event.getPath(), this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public childrenWat1cher(ZooKeeper zk) {
			super();
			this.zk = zk;
		}

	}

}
