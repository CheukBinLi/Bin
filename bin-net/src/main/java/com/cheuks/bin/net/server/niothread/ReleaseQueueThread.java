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
		// //System.err.println("连接器启动");
		//System.out.println("ReleaseQueue-Dispatcher");
		while (!this.shutdown.get()) {
			it = RELEASE_Queue.iterator();
			now = System.currentTimeMillis();
			// //System.err.println(new SimpleDateFormat("HH:mm:ss").format(new
			// Date(now)));
			//						System.err.println(RELEASE_Queue.size());
			while (it.hasNext()) {
				release = it.next();
				key = release.getKey();
				attachment = release.getAttachment();
				if (attachment.isLock() && attachment.isConnectionTimeOut(now, this.timeOut) || !key.isValid()) {
					releaseConnection(key, release);
				}
			}
			try {
				Thread.sleep((long) (timeOut * 0.3));
			} catch (InterruptedException e) {
				// Logger.getDefault().error(this.getClass(), e);
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
			// Logger.getDefault().info(this.getClass(), e);
		} finally {
			RELEASE_Queue.remove(release);
		}
		return false;
	}

}
