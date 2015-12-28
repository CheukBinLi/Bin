package com.cheuks.bin.net.server.niothread;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.Iterator;

public class ReleaseQueueThread extends AbstractControlThread {

	final long timeOut;

	public ReleaseQueueThread(long timeOut) {
		super();
		this.timeOut = timeOut;
	}

	@Override
	public void run() {
		Iterator<Release> it;
		Release release;
		long now;
		while (!this.shutdown.get()) {
			it = RELEASE_Queue.iterator();
			now = System.currentTimeMillis();
			while (it.hasNext()) {
				release = it.next();
				key = release.getKey();
				attachment = release.getAttachment();
				if (!attachment.isLock() && attachment.isConnectionTimeOut(now, this.timeOut)) {
					releaseConnection(key, release);
				}
			}
			try {
				Thread.sleep((long) (timeOut * 0.3));
			} catch (InterruptedException e) {
				break;
			}
			it = null;
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
		} finally {
			RELEASE_Queue.remove(release);
		}
		return false;
	}

}
