package com.cheuks.bin.net.server.niothread;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.util.concurrent.TimeUnit;

public class HandlerQueueThread extends AbstractControlThread {

	private SelectionKey key;

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			try {
				if (null != (key = HANDLER_QUEUE.poll(pollInterval, TimeUnit.MICROSECONDS))) {
					attachment = getAddition(key);
					key = EVENT_LIST.get(TYPE_LIST.get(attachment.getServiceCode())).getHandleEvent().process(key, cache, cacheTag, SERVICE_HANDLER_MAP);
					attachment = getAddition(key);
					attachment.unLockAndUpdateHeartBeat(key, attachment.getRegister(), attachment.getAttachment());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ClosedChannelException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} catch (Throwable e) {
				e.printStackTrace();
			}

		}

	}

}
