package com.cheuks.bin.net.server.niothread;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.util.concurrent.TimeUnit;

import com.cheuks.bin.net.server.handler.MessageInfo;
import com.cheuks.bin.net.server.handler.ServiceHandler;

public class HandlerQueueThread extends AbstractControlThread {

	private SelectionKey key;

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			Object result = null;
			ServiceHandler serviceHandler = null;
			try {
				if (null != (key = HANDLER_QUEUE.poll(5, TimeUnit.MICROSECONDS))) {
					MessageInfo messageInfo = getAddition(key).getMessageInfo();
					Method m = cache.get4Map(cacheTag, messageInfo.getPath(), messageInfo.getMethod());
					serviceHandler = SERVICE_HANDLER_MAP.get(messageInfo.getPath());
					if (null != m && null != serviceHandler)
						try {
							result = m.invoke(serviceHandler, messageInfo.getParams());
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					System.err.println(1);
					key.channel().register(key.selector(), SelectionKey.OP_WRITE, attachment.unLock());
					System.err.println(2);
					// attachment.unLockAndUpdateHeartBeat(key,
					// SelectionKey.OP_WRITE, messageInfo.setResult(result));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ClosedChannelException e) {
				e.printStackTrace();
			}

		}

	}

}
