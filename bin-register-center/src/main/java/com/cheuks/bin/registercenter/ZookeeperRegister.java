package com.cheuks.bin.registercenter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZookeeperRegister extends AbstractRegister<ZooKeeper> {

	private ZooKeeper connection = null;

	public String register(String node) {
		return null;
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

	public boolean existi(String o) throws Throwable {
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

	public static void main(String[] args) throws Throwable {
		String ip = "192.168.168.43:2181,192.168.168.59:2181,192.168.168.60:2181";
		AbstractRegister<ZooKeeper> r = new ZookeeperRegister();
		r.newConnection(true, ip, 5000);
		System.out.println(r.existi("/"));
		System.out.println(Arrays.toString(r.childList("/").toArray()));
	}

}
