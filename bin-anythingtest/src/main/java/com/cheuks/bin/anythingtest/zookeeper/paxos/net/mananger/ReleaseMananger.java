package com.cheuks.bin.anythingtest.zookeeper.paxos.net.mananger;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.Iterator;
import java.util.Map.Entry;

import com.cheuks.bin.anythingtest.zookeeper.paxos.net.ConnectionMsg;

public class ReleaseMananger extends AbstractMananger implements Runnable {

	public ReleaseMananger() {
		super();
	}

	public ReleaseMananger(long interval) {
		super();
		this.interval = interval;
	}

	private long interval = 60000;

	public void run() {
		Iterator<Entry<String, SelectionKey>> it;
		Entry<String, SelectionKey> en;
		SelectionKey key;
		ConnectionMsg msg;
		long now;
		System.err.println("连接器启动");
		while (!Thread.interrupted()) {
			now = System.currentTimeMillis();
			it = HeartBeat.entrySet().iterator();
			while (it.hasNext()) {
				if (lock.tryLock()) {
					try {
						en = it.next();
						key = en.getValue();
						msg = (ConnectionMsg) key.attachment();
						if (msg.isSelectable() && msg.isConnectionTimeOut(now, interval))
							try {
								key.cancel();
								key.channel().close();
							} catch (IOException e) {
								e.printStackTrace();
							} finally {
								HeartBeat.remove(en.getKey());
							}
					} finally {
						lock.unlock();
					}
				}
			}
			try {
				Thread.sleep((long) (interval * 0.3));
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
	}

}
