package com.cheuks.bin.net.server.niothread;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.cheuks.bin.net.util.ByteBufferUtil;
import com.cheuks.bin.net.util.Serializ;

public class WriterThreadMananger extends AbstractControlThread {

	private boolean autoControl = true;
	private int defaultConcurrentCount = Runtime.getRuntime().availableProcessors();
	private int maxConcurrentCount = Runtime.getRuntime().availableProcessors() * 2;
	private AtomicInteger currentCount = new AtomicInteger();
	private Object syncObj = new Object();
	private ExecutorService executorService = Executors.newFixedThreadPool(maxConcurrentCount);

	public WriterThreadMananger() {
		super();
	}

	public WriterThreadMananger(boolean autoControl, int defaultConcurrentCount) {
		super();
		this.autoControl = autoControl;
		this.defaultConcurrentCount = defaultConcurrentCount;
	}

	public WriterThreadMananger setAutoControl(boolean autoControl) {
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
	public void setUncaughtExceptionHandler(UncaughtExceptionHandler eh) {
		super.setUncaughtExceptionHandler(eh);
		executorService.shutdownNow();
	}

	@Override
	public void run() {
		for (int i = 0; i < defaultConcurrentCount; i++, currentCount.addAndGet(1))
			executorService.submit(new Dispatcher());
		while (!this.shutdown.get()) {
			synchronized (syncObj) {
				try {
					if (autoControl) {
						if (WRITER_QUEUE.size() > 200 && currentCount.get() < maxConcurrentCount) {
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
		private SelectionKey key;
		private Serializ serializ;

		@Override
		public void run() {
			while (!Thread.interrupted()) {
				try {
					if (null != (key = WRITER_QUEUE.poll(pollInterval, TimeUnit.MICROSECONDS))) {
						attachment = (Attachment) key.attachment();
						try {
							if (null != attachment.getAttachment()) {
								key = EVENT_LIST.get(attachment.getAttachment().getServiceType()).getWriteEvent().process(key, serializ);
								if (!key.isValid())
									continue;
							}
							else {
								((SocketChannel) key.channel()).write(ByteBufferUtil.newInstance().createPackageByByteBuffer(null));
								attachment.registerRead();
							}
							attachment.unLockAndUpdateHeartBeat(key, attachment.getRegister(), null);
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (ClosedChannelException e) {
						} finally {
							tryDo(RELEASE, key);
						}
					}
				} catch (InterruptedException e) {
					break;
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
