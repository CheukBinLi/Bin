package com.cheuks.bin.net.server.niothread;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.cheuks.bin.net.util.ByteBufferUtil;
import com.cheuks.bin.net.util.ByteBufferUtil.DataPacket;

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
	public void setUncaughtExceptionHandler(UncaughtExceptionHandler eh) {
		super.setUncaughtExceptionHandler(eh);
		executorService.shutdownNow();
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
					}
					else {
						syncObj.wait();
					}
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					executorService.shutdownNow();
					break;
				}
			}
		}
	}

	class Dispatcher extends AbstractControlThread {

		public void run() {
			while (!Thread.interrupted()) {
				try {
					if (null != (key = READER_QUEUE.poll(pollInterval, TimeUnit.MICROSECONDS))) {
						attachment = (Attachment) key.attachment();
						try {
							attachment = (Attachment) key.attachment();
							channel = (SocketChannel) key.channel();
							DataPacket dataPacket = ByteBufferUtil.newInstance().getData(channel, true);
							attachment.setAttachment(dataPacket);
							tryDo(HANDLER, key);
						} catch (NumberFormatException e) {
						} catch (ClosedChannelException e) {
							e.printStackTrace();
						} catch (IOException e) {
						} catch (Throwable e) {
							e.printStackTrace();
						} finally {
							tryDo(RELEASE, key);
						}
					}
				} catch (InterruptedException e) {
					break;
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
