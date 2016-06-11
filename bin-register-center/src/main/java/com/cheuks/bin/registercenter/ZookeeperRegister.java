package com.cheuks.bin.registercenter;

import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZookeeperRegister extends AbstractRegister<ZooKeeper, CreateMode> {

	private ZooKeeper connection = null;

	public String register(String node, byte[] value) throws Throwable {
		String[] path = paths(node, "/");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < path.length - 1; i++) {
			sb.append("/").append(path[i]);
			if (!exists(sb.toString())) {
				this.create(sb.toString(), new byte[0], CreateMode.PERSISTENT);
			}
		}
		return this.create(node, value, CreateMode.EPHEMERAL);

	}

	@Override
	public ZooKeeper newConnection(boolean setConnection, Object... o) throws Throwable {
		final CountDownLatch count = new CountDownLatch(1);
		final ZooKeeper zkClient = new ZooKeeper((String) getValue(o[0]), (Integer) getValue(o[1]), new Watcher() {
			public void process(WatchedEvent event) {
				count.countDown();
			}
		});
		count.await();
		if (setConnection)
			this.connection = zkClient;
		return zkClient;
	}

	public boolean exists(String o) throws Throwable {
		Stat stat = this.connection.exists(o.toString(), true);
		return null != stat;
	}

	public List<?> childList(String o) throws KeeperException, InterruptedException {
		return this.connection.getChildren(o.toString(), true);
	}

	public ZooKeeper getConnection() {
		return connection;
	}

	public ZookeeperRegister setConnection(ZooKeeper connection) {
		this.connection = connection;
		return this;
	}

	public String create(String node, byte[] value, CreateMode createModel) throws Throwable {
		return this.connection.create(node, value, Ids.OPEN_ACL_UNSAFE, createModel);
	}

	public static void main(String[] args) throws Throwable {
		CountDownLatch count = new CountDownLatch(1);
		String ip = "192.168.168.43:2181,192.168.168.59:2181,192.168.168.60:2181";
		AbstractRegister<ZooKeeper, CreateMode> r = new ZookeeperRegister();
		r.newConnection(true, ip, 5000);
		r.register("/A/B/C/D/E", "Hello".getBytes());
		System.out.println(r.exists("/"));
		System.out.println(Arrays.toString(r.childList("/").toArray()));
		count.await();
		//		final CountDownLatch countDownLatch = new CountDownLatch(1);
		//		ZooKeeper zkClient = new ZooKeeper(ip, 5000, new Watcher() {
		//
		//			public void process(WatchedEvent event) {
		//				System.out.println(event);
		//				countDownLatch.countDown();
		//			}
		//
		//		});
		//		countDownLatch.await();
		//		Stat stat;
		//		if (null == (stat = zkClient.exists("/listening_path", false)))
		//			zkClient.create("/listening_path", new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		//		zkClient.create("/listening_path/ip", new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		//		Thread.sleep(15000);
		//		// zkClient.delete("/listening_path", -1);
		//		zkClient.close();
	}

}
