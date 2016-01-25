package com.cheuks.bin.net.server.niothread;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HandlerQueueThread extends AbstractControlThread {

	private boolean autoControl = true;
	private int defaultConcurrentCount = Runtime.getRuntime().availableProcessors();
	private int maxConcurrentCount = Runtime.getRuntime().availableProcessors() * 2;
	private AtomicInteger currentCount = new AtomicInteger();
	private Object syncObj = new Object();
	private ExecutorService executorService = Executors.newFixedThreadPool(maxConcurrentCount);

	public HandlerQueueThread() {
		super();
	}

	public HandlerQueueThread(boolean autoControl, int defaultConcurrentCount) {
		super();
		this.autoControl = autoControl;
		this.defaultConcurrentCount = defaultConcurrentCount;
	}

	public HandlerQueueThread setAutoControl(boolean autoControl) {
		synchronized (syncObj) {
			if (autoControl)
				syncObj.notify();
			this.autoControl = autoControl;
		}
		return this;
	}

	@Override
	public void interrupt() {
		executorService.shutdownNow();
		super.interrupt();
	}

	@Override
	public void run() {
		//System.out.println("HandlerQueue");
		for (int i = 0; i < defaultConcurrentCount; i++, currentCount.addAndGet(1))
			executorService.submit(new Dispatcher());
		while (!this.shutdown.get()) {
			synchronized (syncObj) {
				try {
					if (autoControl) {
						if (HANDLER_QUEUE.size() > 200 && currentCount.get() < maxConcurrentCount) {
							executorService.submit(new Dispatcher());
						}
					}
					else {
						syncObj.wait();
					}
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					executorService.shutdownNow();
					// Logger.getDefault().error(this.getClass(), e);
					break;
				}
			}
		}
		//System.out.println("HandlerQueueThread结束");
	}

	class Dispatcher extends AbstractControlThread {
		private SelectionKey key;

		@Override
		public void setSerializ(Class<?> serializ) {
			super.setSerializ(serializ);
		}

		@Override
		public void run() {
			while (!Thread.interrupted()) {
				try {
					if (null != (key = HANDLER_QUEUE.poll(pollInterval, TimeUnit.MICROSECONDS))) {
						attachment = getAddition(key);
						if (null != attachment.getAttachment()) {
							key = EVENT_LIST.get(attachment.getAttachment().getServiceType()).getHandleEvent().process(key, serializClass, cache, cacheTag, SERVICE_HANDLER_MAP);
							attachment = getAddition(key);
							attachment.unLockAndUpdateHeartBeat(key, attachment.getRegister(), attachment.getAttachment());
						}
						else
							attachment.unLockAndUpdateHeartBeat(key, SelectionKey.OP_WRITE, null);
					}
				} catch (InterruptedException e) {
					// e.printStackTrace();
					break;
				} catch (ClosedChannelException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
					break;
				} catch (Throwable e) {
					e.printStackTrace();
					break;
				}
			}
		}
	}

}
