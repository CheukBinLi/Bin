package com.cheuks.bin.net.server.niothread;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.util.concurrent.TimeUnit;

import com.cheuks.bin.net.server.handler.ServiceHandler;

public class HandlerQueueThread extends AbstractControlThread {

	private SelectionKey key;
	private ServiceHandler serviceHandler = null;
	private Object result = null;

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			try {
				if (null != (key = HANDLER_QUEUE.poll(5, TimeUnit.MICROSECONDS))) {
					attachment = getAddition(key);
					Method m = cache.get4Map(cacheTag, attachment.getMessageInfo().getPath(), attachment.getMessageInfo().getMethod());
					serviceHandler = SERVICE_HANDLER_MAP.get(attachment.getMessageInfo().getPath());
					if (null != m && null != serviceHandler)
						try {
							result = m.invoke(serviceHandler, attachment.getMessageInfo().getParams());
							attachment.getMessageInfo().setResult(result);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					//							key.channel().register(key.selector(), SelectionKey.OP_WRITE, attachment.unLock());
					attachment.unLockAndUpdateHeartBeat(key, SelectionKey.OP_WRITE, attachment.getMessageInfo());

				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ClosedChannelException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
