package com.cheuks.bin.net.server.niothread;

import java.util.concurrent.TimeUnit;

public class ReleaseListThread extends AbstractControlThread {

	@Override
	public void run() {
		while (!this.shutdown.get()) {
			try {
				if (null != (key = RELEASE_LIST.pollFirst(5, TimeUnit.MICROSECONDS))) {
					this.attachment = (Attachment) key.attachment();
					attachment.unLockAndUpdateHeartBeat();
					RELEASE_Queue.add(new Release(this.attachment.getId(), this.attachment, key));
				}
			} catch (InterruptedException e) {
				break;
			}
		}
	}

}
