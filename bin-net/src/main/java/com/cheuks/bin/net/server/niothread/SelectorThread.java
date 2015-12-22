package com.cheuks.bin.net.server.niothread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

import com.cheuks.bin.util.Logger;

public class SelectorThread extends AbstractControlThread {

	// Map<Integer, Boolean> portInfo;
	ServerSocketChannel serverSocketChannel;
	Selector selector;
	Integer[] port;
	final Integer maxConnection;
	final Long interval;

	//	@Override
	//	public void setUncaughtExceptionHandler(UncaughtExceptionHandler eh) {
	//		try {
	//			Selector.open().close();
	//		} catch (IOException e) {
	//			Logger.getDefault().error(this.getClass(), e);
	//		}
	//		super.setUncaughtExceptionHandler(eh);
	//	}

	public SelectorThread(Long selectInterval, Integer... port) {
		super();
		this.port = port;
		this.interval = selectInterval;
		this.maxConnection = 2000;
	}

	public SelectorThread(Integer maxConnection, Long selectInterval, Integer... port) {
		super();
		this.port = port;
		this.interval = selectInterval;
		this.maxConnection = maxConnection;
	}

	void init() {
		clearAll();
		try {
			addListener(this.port);
		} catch (IOException e) {
			Logger.getDefault().error(this.getClass(), e);
		}
	}

	public synchronized void addListener(Integer... port) throws IOException {
		if (null == selector)
			selector = Selector.open();
		for (int i = 0; i < port.length; i++) {
			SERVER_LIST.add(serverSocketChannel = ServerSocketChannel.open());
			serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
			serverSocketChannel.socket().setSoTimeout(60000);
			serverSocketChannel.bind(new InetSocketAddress(port[i]));
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		}
		//		System.out.println(serverSocketChannel.supportedOptions());
	}

	@Override
	public void run() {
		init();
		while (!Thread.interrupted())
			try {
				select(this.interval);
				Thread.sleep(10);
			} catch (IOException e) {
				Logger.getDefault().error(this.getClass(), e);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

	void select(long interval) throws IOException {
		selector.select(interval);
		Set<SelectionKey> keys = selector.selectedKeys();
		Iterator<SelectionKey> it = keys.iterator();
		while (it.hasNext()) {
			key = it.next();
			it.remove();
			if (!key.isValid())
				continue;
			else if (key.isAcceptable()) {
				if (this.maxConnection <= RELEASE_Queue.size()) {
					key.interestOps(SelectionKey.OP_ACCEPT);
					continue;
				}
				attachment = createAttachment().updateHeartBeatAndSetActionType(Attachment.AT_READING);
				channel = ((ServerSocketChannel) key.channel()).accept();
				channel.configureBlocking(false);
				channel.finishConnect();
				key = channel.register(key.selector(), SelectionKey.OP_READ, attachment);
				tryDo(RELEASE, key);
				continue;
			}
			else if (!getAddition(key).isLock()) {
				if (key.isReadable() && getAddition(key).lockSetActionTypeAnd(Attachment.AT_READING, Attachment.AT_WRITING)) {
					tryDo(READER, key);
				}
				else if (key.isWritable() && getAddition(key).lockSetActionTypeAnd(Attachment.AT_WRITING, Attachment.AT_READING)) {
					tryDo(WRITER, key);
				}
				continue;
			}
			//			else if (key.isConnectable()) {
			//				if (this.maxConnection <= RELEASE_Queue.size()) {
			//					//					key.channel().configureBlocking(false);
			//					key.channel().register(selector, SelectionKey.OP_CONNECT);
			//					continue;
			//				}
			//				else {
			//					key.channel().register(selector, SelectionKey.OP_ACCEPT);
			//				}
			//			}
		}
	}

}
