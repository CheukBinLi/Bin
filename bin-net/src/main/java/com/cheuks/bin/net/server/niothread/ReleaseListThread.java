package com.cheuks.bin.net.server.niothread;

import com.cheuks.bin.util.Logger;

public class ReleaseListThread extends AbstractControlThread {

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			try {
				key = RELEASE_LIST.takeFirst();
				if (null != key.attachment()) {
					this.attachment = (Attachment) key.attachment();
				} else
					throw new NullPointerException("Attachment不能为空");
				RELEASE_Queue.add(new Release(attachment.getId(), attachment, key));
			} catch (InterruptedException e) {
				Logger.getDefault().error(this.getClass(), e);
			}
		}
	}

}
