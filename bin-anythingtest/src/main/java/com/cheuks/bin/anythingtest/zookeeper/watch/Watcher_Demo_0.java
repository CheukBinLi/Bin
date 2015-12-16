package com.cheuks.bin.anythingtest.zookeeper.watch;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class Watcher_Demo_0 {

	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		final CountDownLatch countDownLatch1 = new CountDownLatch(1);
		final ZooKeeperEX zkClient = new ZooKeeperEX(ServerList.getList(), 5000, null);
		zkClient.setWatcher(new WatcherEx(zkClient, countDownLatch1));
		countDownLatch1.await();
		if (null != zkClient.exists("/listening_path", false)) {
			Iterator<String> it = zkClient.getChildren("/listening_path", true).iterator();
			String childrenPath;
			while (it.hasNext()) {
				childrenPath = "/listening_path/" + it.next();
				zkClient.exists(childrenPath, true);
			}
		}
		else
			zkClient.exists("/listening_path", true);
		countDownLatch.await();
	}

	static class WatcherEx implements Watcher {

		private ZooKeeper zk;
		private CountDownLatch countDownLatch;

		public void process(WatchedEvent event) {
			if (this.countDownLatch.getCount() > 0)
				this.countDownLatch.countDown();
			if (event.getState() == KeeperState.SyncConnected && event.getType() == EventType.None) {
				System.err.println("连接");
				return;
			}
			// System.err.println(String.format("#%s", name));
			System.err.println(String.format("----------%s%s", "Path:", event.getPath()));
			System.err.println(String.format("----------%s%s", "State:", event.getState()));
			System.err.println(String.format("----------%s%s", "Type:", event.getType()));
			System.err.println(String.format("----------%s%s", "Wrapper:", event.getWrapper()));
			try {
				if (null != event.getPath()) {
					zk.exists(event.getPath(), true);
					List<String> children = null;
					if (event.getType() == EventType.NodeDeleted) {
						String parentPath = event.getPath().substring(0, event.getPath().lastIndexOf("/"));
						System.out.println(parentPath);
						if (parentPath.length() > 0)
							children = zk.getChildren(parentPath, true);
					}
					else
						children = zk.getChildren(event.getPath(), true);
					if (null != children) {
						Iterator<String> it = children.iterator();
						String childrenPath;
						while (it.hasNext()) {
							childrenPath = event.getPath() + "/" + it.next();
							zk.exists(childrenPath, true);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public WatcherEx(ZooKeeper zk, CountDownLatch countDownLatch) {
			super();
			this.zk = zk;
			this.countDownLatch = countDownLatch;
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
