package com.cheuks.bin.net.server;

import java.io.IOException;
import java.net.InetSocketAddress;
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
	int[] port;
	final long interval;

	@Override
	public void setUncaughtExceptionHandler(UncaughtExceptionHandler eh) {
		try {
			Selector.open().close();
		} catch (IOException e) {
			Logger.getDefault().error(this.getClass(), e);
		}
		super.setUncaughtExceptionHandler(eh);
	}

	public SelectorThread(long selectInterval, int... port) {
		super();
		this.port = port;
		this.interval = selectInterval;
	}

	void init() {
		try {
			selector = Selector.open();
			for (int i = 0; i < port.length; i++) {
				SERVER_LIST.add(serverSocketChannel = ServerSocketChannel.open());
				serverSocketChannel.socket().setReuseAddress(true);
				serverSocketChannel.bind(new InetSocketAddress(port[i]));
				serverSocketChannel.configureBlocking(false);
				serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			}
		} catch (Exception e) {
			Logger.getDefault().error(this.getClass(), e);
		}
	}

	@Override
	public void run() {
		init();
		System.err.println("侦听");
		while (!Thread.interrupted())
			try {
				select(this.interval);
			} catch (IOException e) {
				Logger.getDefault().error(this.getClass(), e);
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
				attachment = createAttachment().updateHeartBeatAndSetActionType(Attachment.AT_READING);
				channel = ((ServerSocketChannel) key.channel()).accept();
				channel.configureBlocking(false);
				channel.finishConnect();
				key = channel.register(key.selector(), SelectionKey.OP_READ, attachment);
				tryDo(RELEASE, key);
				continue;
			} else if (!getAddition(key).isLock()) {
				if (key.isReadable() && getAddition(key).lockSetActionTypeAnd(Attachment.AT_READING, Attachment.AT_WRITING)) {
					tryDo(READER, key);
				} else if (key.isWritable() && getAddition(key).lockSetActionTypeAnd(Attachment.AT_WRITING, Attachment.AT_READING)) {
					tryDo(WRITER, key);
				}
				continue;
			}
		}
	}

}
