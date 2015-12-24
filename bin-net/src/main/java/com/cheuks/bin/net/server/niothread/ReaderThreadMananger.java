package com.cheuks.bin.net.server.niothread;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.cheuks.bin.net.server.handler.MessageInfo;
import com.cheuks.bin.net.server.handler.ServiceHandler;
import com.cheuks.bin.net.util.ByteBufferUtil;
import com.cheuks.bin.net.util.DefaultSerializImpl;
import com.cheuks.bin.net.util.Serializ;
import com.cheuks.bin.util.Logger;

public class ReaderThreadMananger extends AbstractControlThread {

	private boolean autoControl = true;
	private int defaultConcurrentCount = Runtime.getRuntime().availableProcessors();
	private int maxConcurrentCount = Runtime.getRuntime().availableProcessors() * 2;
	private AtomicInteger currentCount = new AtomicInteger();
	private Object syncObj = new Object();
	private ExecutorService executorService = Executors.newFixedThreadPool(maxConcurrentCount);
	private final Serializ serializ;

	public ReaderThreadMananger() {
		super();
		this.serializ = new DefaultSerializImpl();
	}

	public ReaderThreadMananger(boolean autoControl, int defaultConcurrentCount) {
		super();
		this.autoControl = autoControl;
		this.defaultConcurrentCount = defaultConcurrentCount;
		this.serializ = new DefaultSerializImpl();
	}

	public ReaderThreadMananger(boolean autoControl, int defaultConcurrentCount, final Serializ serializ) {
		super();
		this.autoControl = autoControl;
		this.defaultConcurrentCount = defaultConcurrentCount;
		this.serializ = serializ;
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
					}
					else {
						syncObj.wait();
					}
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					Logger.getDefault().error(this.getClass(), e);
				}
			}
		}
	}

	class Dispatcher extends AbstractControlThread {
		private boolean flags;
		private SelectionKey key;
		private Attachment attachment;
		private ServiceHandler serviceHandler = null;
		private Object result = null;

		public void run() {
			while (!Thread.interrupted()) {
				try {
					if (null != (key = READER_QUEUE.poll(5, TimeUnit.MICROSECONDS))) {
						attachment = (Attachment) key.attachment();
						try {
							channel = (SocketChannel) key.channel();
							channel.configureBlocking(false);
							ByteArrayOutputStream out = ByteBufferUtil.getByte(channel);
							if (flags = (null == out))
								continue;
							// System.out.println(new
							// String(out.toByteArray()));
							attachment.setMessageInfo((MessageInfo) serializ.toObject(out));
							// System.out.println(attachment.getMessageInfo().getPath());
							///
							tryDo(HANDLER, key);
							//handler

							//							Method m = cache.get4Map(cacheTag, attachment.getMessageInfo().getPath(), attachment.getMessageInfo().getMethod());
							//							serviceHandler = SERVICE_HANDLER_MAP.get(attachment.getMessageInfo().getPath());
							//							if (null != m && null != serviceHandler)
							//								try {
							//									result = m.invoke(serviceHandler, attachment.getMessageInfo().getParams());
							//									attachment.getMessageInfo().setResult(result);
							//								} catch (IllegalAccessException e) {
							//									e.printStackTrace();
							//								} catch (IllegalArgumentException e) {
							//									e.printStackTrace();
							//								} catch (InvocationTargetException e) {
							//									e.printStackTrace();
							//								}
							//							System.err.println(1);
							//							//							key.channel().register(key.selector(), SelectionKey.OP_WRITE, attachment.unLock());
							//							attachment.unLockAndUpdateHeartBeat(key, SelectionKey.OP_WRITE, attachment.getMessageInfo());
							//							System.err.println(2);

						} catch (NumberFormatException e) {
							// e.printStackTrace();
						} catch (ClosedChannelException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (Throwable e) {
							e.printStackTrace();
						} finally {
							//							if (flags)
							//								tryDo(RELEASE, key);
							//							flags = true;
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
