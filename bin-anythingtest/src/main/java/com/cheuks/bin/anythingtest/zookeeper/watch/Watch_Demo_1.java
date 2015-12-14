package com.cheuks.bin.anythingtest.zookeeper.watch;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.ZooDefs.Ids;

public class Watch_Demo_1 {

	public static void main(String[] args) throws KeeperException, InterruptedException, IOException {
		ZooKeeper zkClient = new ZooKeeper(ServerList.getList(), 5000, null);
		Stat stat;
		if (null == (stat = zkClient.exists("/listening_path", false)))
			zkClient.create("/listening_path", new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zkClient.create("/listening_path/ip", new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		Thread.sleep(5000);
		// zkClient.delete("/listening_path", -1);
		zkClient.close();
	}

}
