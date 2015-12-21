package com.cheuks.bin.net.server.niothread;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.cheuks.bin.net.util.ByteBufferUtil;
import com.cheuks.bin.util.Logger;

public class ReaderThreadMananger extends AbstractControlThread {

	private boolean autoControl = true;
	private int defaultConcurrentCount = Runtime.getRuntime().availableProcessors();
	private int maxConcurrentCount = Runtime.getRuntime().availableProcessors() * 2;
	private AtomicInteger currentCount = new AtomicInteger();
	private Object syncObj = new Object();
	private ExecutorService executorService = Executors.newFixedThreadPool(maxConcurrentCount);

	public ReaderThreadMananger() {
		super();
	}

	public ReaderThreadMananger(boolean autoControl, int defaultConcurrentCount) {
		super();
		this.autoControl = autoControl;
		this.defaultConcurrentCount = defaultConcurrentCount;
	}

	public ReaderThreadMananger setAutoControl(boolean autoControl) {
		synchronized (syncObj) {
			if (autoControl)
				syncObj.notify();
			this.autoControl = autoControl;
		}
		return this;
	}

	@Override
	public void interrupt() {
		executorService.shutdown();
		super.interrupt();
	}

	@Override
	public void run() {
		for (int i = 0; i < defaultConcurrentCount; i++, currentCount.addAndGet(1))
			executorService.submit(new Dispatcher());
		while (!Thread.interrupted()) {
			synchronized (syncObj) {
				try {
					if (autoControl) {
						if (READER_QUEUE.size() > 200 && currentCount.get() < maxConcurrentCount) {
							executorService.submit(new Dispatcher());
						}
					} else {
						syncObj.wait();
					}
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					Logger.getDefault().error(this.getClass(), e);
				}
			}
		}
	}

	static class Dispatcher extends AbstractControlThread {
		@Override
		public void run() {
			boolean flags = false;
			while (!Thread.interrupted()) {
				flags = false;
				try {
					if (null != (key = READER_QUEUE.poll(5, TimeUnit.MICROSECONDS))) {
						attachment = (Attachment) key.attachment();
						try {
							channel = (SocketChannel) key.channel();
							channel.configureBlocking(false);
							ByteArrayOutputStream out = ByteBufferUtil.getByte(channel);
							// System.out.println(new
							// String(out.toByteArray()));
							attachment.unLockAndUpdateHeartBeat(channel, key, SelectionKey.OP_WRITE, null);
							flags = true;
						} catch (NumberFormatException e) {
							// e.printStackTrace();
						} catch (ClosedChannelException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							if (null == key)
								continue;
							if (!flags)
								key.attach(getAddition(key).unLock());
							tryDo(RELEASE, key);
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public int getDefaultConcurrentCount() {
		return defaultConcurrentCount;
	}

	public int getMaxConcurrentCount() {
		return maxConcurrentCount;
	}

	public Integer getCurrentCount() {
		return currentCount.get();
	}

	public boolean isAutoControl() {
		return autoControl;
	}
}
