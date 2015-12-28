package com.cheuks.bin.net.server.niothread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.StandardSocketOptions;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SelectorThread extends AbstractControlThread {

	ServerSocketChannel serverSocketChannel;
	Selector selector;
	List<Integer[]> port;
	final Integer maxConnection;
	final Long interval;

	public SelectorThread(Long selectInterval, List<Integer[]> port) {
		super();
		this.port = port;
		this.interval = selectInterval;
		this.maxConnection = 2000;
		init();
	}

	public SelectorThread(Integer maxConnection, Long selectInterval, List<Integer[]> port) {
		super();
		this.port = port;
		this.interval = selectInterval;
		this.maxConnection = maxConnection;
		init();
	}

	void init() {
		clearAll();
		if (null == selector)
			try {
				selector = Selector.open();
				if (!this.port.isEmpty())
					for (int i = 0; i < port.size(); i++) {
						addListener(port.get(i)[0], port.get(i)[1]);
					}
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (ClosedChannelException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

	}

	public synchronized void addListener(Integer port, Integer serviceType) throws SocketException, ClosedChannelException, IOException {
		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
		serverSocketChannel.socket().setReuseAddress(true);
		serverSocketChannel.socket().setSoTimeout(60000);
		serverSocketChannel.bind(new InetSocketAddress(port));
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		TYPE_LIST.put(serverSocketChannel.hashCode(), serviceType);
		SERVER_LIST.add(serverSocketChannel);
	}

	@Override
	public void interrupt() {
		super.interrupt();
	}

	@Override
	public void run() {
		while (!this.shutdown.get()) {
			try {
				select(this.interval);
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				break;
			}
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
				attachment = createAttachment().updateHeartBeatAndSetActionTypeAndServiceCode(Attachment.AT_READING, key);
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
		}
	}

}
