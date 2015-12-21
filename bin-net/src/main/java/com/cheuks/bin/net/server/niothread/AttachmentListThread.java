package com.cheuks.bin.net.server.niothread;

import java.util.UUID;

import com.cheuks.bin.util.Logger;

public class AttachmentListThread extends AbstractControlThread {

	private int mix;

	public AttachmentListThread(int mix) {
		super();
		this.mix = mix;
	}

	String getUUID() {
		return UUID.randomUUID().toString();
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			synchronized (ATTACHMENT_LIST) {
				if (ATTACHMENT_LIST.size() < this.mix)
					ATTACHMENT_LIST.offerLast(new Attachment(getUUID()));
				else
					try {
						ATTACHMENT_LIST.wait();
					} catch (InterruptedException e) {
						Logger.getDefault().error(this.getClass(), e);
					}
			}
		}
	}

}
