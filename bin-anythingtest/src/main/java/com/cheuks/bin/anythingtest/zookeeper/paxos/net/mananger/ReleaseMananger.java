package com.cheuks.bin.anythingtest.zookeeper.paxos.net.mananger;

import com.cheuks.bin.anythingtest.zookeeper.paxos.net.ConnectionMsg;
import com.cheuks.bin.anythingtest.zookeeper.paxos.net.Logger;
import com.cheuks.bin.anythingtest.zookeeper.paxos.net.Release;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.Iterator;

public class ReleaseMananger extends AbstractMananger implements Runnable {

	public ReleaseMananger() {
		super();
	}

	public ReleaseMananger(long interval) {
		super();
		this.interval = interval;
		this.longInterval = this.interval * 10;
	}

	public ReleaseMananger(long interval, long longInterval) {
		super();
		this.interval = interval;
		this.longInterval = longInterval;
	}

	private long interval = 2000;
	private long longInterval = interval * 10;

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
			// System.out.println(HeartBeat.size());
			while (it.hasNext()) {
				release = it.next();
				key = release.getKey();
				msg = release.getMsg();

				// System.out.print(release.getName() + " " + msg.isSelectable()
				// + " ");

				if (msg.isSelectable() && msg.isConnectionTimeOut(now, this.interval)) {
					if (releaseConnection(key, release))
						continue;
				} else if (msg.isConnectionTimeOut(now, this.longInterval)) {
					if (releaseConnection(key, release))
						continue;
				}
				// System.out.println(msg.isSelectable());
			}
			try {
				Thread.sleep((long) (interval * 0.3));
			} catch (InterruptedException e) {
				Logger.getDefault().error(this.getClass(), e);
				break;
			}
		}
	}

	private boolean releaseConnection(SelectionKey key, Release release) {
		try {
			if (null != key) {
				key.cancel();
				key.channel().close();
			}
			return true;
		} catch (IOException e) {
			Logger.getDefault().info(this.getClass(), e);
		} finally {
			HeartBeat.remove(release);
			msg = getConnectionMsg(key);
			// Logger.getDefault().info(getClass(), msg.getId() + "结束" +
			// release.getName());
		}
		return false;
	}

}
