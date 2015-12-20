package com.cheuks.bin.net.server;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.Iterator;

import com.cheuks.bin.util.Logger;

public class ReleaseQueueThread extends AbstractControlThread {

	long timeOut;

	public ReleaseQueueThread(long timeOut) {
		super();
		this.timeOut = timeOut;
	}

	@Override
	public void run() {
		Iterator<Release> it;
		Release release;
		long now;
		// System.err.println("连接器启动");
		while (!Thread.interrupted()) {
			it = RELEASE_Queue.iterator();
			now = System.currentTimeMillis();
			while (it.hasNext()) {
				release = it.next();
				key = release.getKey();
				attachment = release.getAttachment();

				if (!attachment.isLock() && attachment.isConnectionTimeOut(now, this.timeOut)) {
					if (releaseConnection(key, release))
						continue;
				}
			}
			try {
				Thread.sleep((long) (timeOut * 0.3));
			} catch (InterruptedException e) {
				Logger.getDefault().error(this.getClass(), e);
				// break;
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
			// Logger.getDefault().info(this.getClass(), e);
		} finally {
			RELEASE_Queue.remove(release);
		}
		return false;
	}

}
