package com.cheuks.bin.anythingtest.zookeeper.paxos.net.mananger;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import com.cheuks.bin.anythingtest.zookeeper.paxos.net.ConnectionMsg;
import com.cheuks.bin.anythingtest.zookeeper.paxos.net.Logger;
import com.cheuks.bin.anythingtest.zookeeper.paxos.net.Release;

public class ReleaseMananger extends AbstractMananger implements Runnable {

	public ReleaseMananger() {
		super();
	}

	public ReleaseMananger(long interval) {
		super();
		this.interval = interval;
	}

	private long interval = 2000;

	public void run() {
		Iterator<Release> it;
		Release release;
		SelectionKey key;
		ConnectionMsg msg;
		long now;
		// System.err.println("连接器启动");
		while (!Thread.interrupted()) {
			it = HeartBeat.iterator();
			now = System.currentTimeMillis();
			while (it.hasNext()) {
				release = it.next();
				key = release.getKey();
				msg = release.getMsg();
				System.err.print(release.getName());
				if (msg.isSelectable() && msg.isConnectionTimeOut(now, this.interval))
					try {
						key.cancel();
						key.channel().close();
					} catch (IOException e) {
						Logger.getDefault().info(this.getClass(), e);
					} finally {
						HeartBeat.remove(release);
						Logger.getDefault().info(getClass(), "结束" + release.getName());
					}
			}
			try {
				Thread.sleep((long) (interval * 0.3));
			} catch (InterruptedException e) {
				Logger.getDefault().error(this.getClass(), e);
				break;
			}
		}
	}

}
