package com.cheuks.bin.anythingtest.zookeeper.watch;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.client.HostProvider;

import java.io.IOException;
import java.lang.reflect.Field;

@SuppressWarnings("rawtypes")
public class ZooKeeperEX extends ZooKeeper {

	static Field defaultWatcher;
	static Field watchManager;

	static {
		try {
			// watchManager
			watchManager = ZooKeeper.class.getDeclaredField("watchManager");
			watchManager.setAccessible(true);
			// defaultWatcher
			Class[] cs = ZooKeeper.class.getDeclaredClasses();
			for (Class c : cs) {
				if (c.getName().contains("ZKWatchManager")) {
					defaultWatcher = c.getDeclaredField("defaultWatcher");
					defaultWatcher.setAccessible(true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * 动态设置侦听器
	 * 
	 * @param watcher
	 * @return
	 */
	public boolean setWatcher(Watcher watcher) {
		if (null == watcher || null == defaultWatcher)
			return false;
		try {
			defaultWatcher.set(watchManager.get(this), watcher);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public ZooKeeperEX(String connectString, int sessionTimeout, Watcher watcher) throws IOException {
		super(connectString, sessionTimeout, watcher);
	}

	public ZooKeeperEX(String connectString, int sessionTimeout, Watcher watcher, boolean canBeReadOnly, HostProvider aHostProvider) throws IOException {
		super(connectString, sessionTimeout, watcher, canBeReadOnly, aHostProvider);
	}

	public ZooKeeperEX(String connectString, int sessionTimeout, Watcher watcher, boolean canBeReadOnly) throws IOException {
		super(connectString, sessionTimeout, watcher, canBeReadOnly);
	}

	public ZooKeeperEX(String connectString, int sessionTimeout, Watcher watcher, long sessionId, byte[] sessionPasswd, boolean canBeReadOnly, HostProvider aHostProvider) throws IOException {
		super(connectString, sessionTimeout, watcher, sessionId, sessionPasswd, canBeReadOnly, aHostProvider);
	}

	public ZooKeeperEX(String connectString, int sessionTimeout, Watcher watcher, long sessionId, byte[] sessionPasswd, boolean canBeReadOnly) throws IOException {
		super(connectString, sessionTimeout, watcher, sessionId, sessionPasswd, canBeReadOnly);
	}

	public ZooKeeperEX(String connectString, int sessionTimeout, Watcher watcher, long sessionId, byte[] sessionPasswd) throws IOException {
		super(connectString, sessionTimeout, watcher, sessionId, sessionPasswd);
	}

	public static void main(String[] args) {
		System.out.println((1 | 6));
		System.out.println((5 | 7));
		System.out.println((6 | 6));
		System.out.println((6 | 6));
		System.out.println((~9));
		System.out.println((~8));
		System.out.println((~7));
		System.out.println((~1));
		System.out.println((~0));
		System.out.println((5 & 5));
		System.out.println((11 & 50));
		System.out.println((12 & 12));
		System.out.println((5 & 9));
		System.out.println((5 & 5));
		System.out.println((-11|132)<0);
	}
}
