package com.cheuks.bin.net.server.niothread;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.cheuks.bin.net.util.ByteBufferUtil;
import com.cheuks.bin.net.util.DefaultSerializImpl;
import com.cheuks.bin.net.util.Serializ;
import com.cheuks.bin.util.Logger;

public class WriterThreadMananger extends AbstractControlThread {

	private static final AtomicInteger a = new AtomicInteger();
	private boolean autoControl = true;
	private int defaultConcurrentCount = Runtime.getRuntime().availableProcessors();
	private int maxConcurrentCount = Runtime.getRuntime().availableProcessors() * 2;
	private AtomicInteger currentCount = new AtomicInteger();
	private Object syncObj = new Object();
	private ExecutorService executorService = Executors.newFixedThreadPool(maxConcurrentCount);
	private final Serializ serializ;

	public WriterThreadMananger() {
		super();
		this.serializ = new DefaultSerializImpl();
	}

	public WriterThreadMananger(boolean autoControl, int defaultConcurrentCount) {
		super();
		this.autoControl = autoControl;
		this.defaultConcurrentCount = defaultConcurrentCount;
		this.serializ = new DefaultSerializImpl();
	}

	public WriterThreadMananger(boolean autoControl, int defaultConcurrentCount, final Serializ serializ) {
		super();
		this.autoControl = autoControl;
		this.defaultConcurrentCount = defaultConcurrentCount;
		this.serializ = serializ;
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
						if (WRITER_QUEUE.size() > 200 && currentCount.get() < maxConcurrentCount) {
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
		private SelectionKey key;

		@Override
		public void run() {
			while (!Thread.interrupted()) {
				try {
					if (null != (key = WRITER_QUEUE.poll(5, TimeUnit.MICROSECONDS))) {
						attachment = (Attachment) key.attachment();
						try {
							channel = (SocketChannel) key.channel();
							channel.configureBlocking(false);
							//							channel.write(ByteBufferUtil.getBuffer(("服务回复：" + a.addAndGet(1)).getBytes()));
							channel.write(ByteBufferUtil.getBuffer(serializ.serializ(attachment.getMessageInfo())));
							key = attachment.unLockAndUpdateHeartBeat(channel, key, SelectionKey.OP_READ, null);
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (ClosedChannelException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (Throwable e) {
							e.printStackTrace();
						} finally {
							tryDo(RELEASE, key);
						}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
