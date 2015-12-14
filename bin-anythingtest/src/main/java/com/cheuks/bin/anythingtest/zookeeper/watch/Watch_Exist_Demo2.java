package com.cheuks.bin.anythingtest.zookeeper.watch;

import java.io.IOException;
import java.util.concurrent.Executors;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.ZooDefs.Ids;

public class Watch_Exist_Demo2 implements Watch {

	public static void main(String[] args) {
		//		Executors.newCachedThreadPool().execute(new listener());
		try {
			ZooKeeper zkClient = new ZooKeeper(ipAddress, 50000, null);
			Stat stat;
			if (null == (stat = zkClient.exists("/mmx", false)))
				zkClient.create("/mmx", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
			else
				zkClient.delete("/mmx", stat.getVersion());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
