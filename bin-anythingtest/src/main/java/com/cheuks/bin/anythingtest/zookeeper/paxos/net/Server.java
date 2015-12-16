package com.cheuks.bin.anythingtest.zookeeper.paxos.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server extends Thread {

	protected Selector selector;
	private SelectionKey selectionKey;
	protected ServerSocketChannel serverSocketChannel;
	private int port;
	private String channelName;

	private SocketChannel socket;

	public Server(int port, String channelName) {
		super();
		this.port = port;
		this.channelName = channelName;

		try {
			selector = Selector.open();
			serverSocketChannel = ServerSocketChannel.open();
			//			serverSocketChannel.socket().setSoTimeout(10000);
			serverSocketChannel.socket().setReuseAddress(true);
			serverSocketChannel.bind(new InetSocketAddress(port));
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (ClosedChannelException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void select() throws Exception {

		selector.select();
		Iterator<SelectionKey> it = selector.selectedKeys().iterator();
		while (it.hasNext()) {
			selectionKey = it.next();
			it.remove();
			if (!selectionKey.isValid())
				continue;
			if (selectionKey.isAcceptable()) {
				socket=
			}
		}

	}

	public void run() {
		try {
			while (!Thread.interrupted())
				select();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setUncaughtExceptionHandler(UncaughtExceptionHandler eh) {
		try {
			Selector.open().close();
		} catch (IOException e) {
			//日志
			e.printStackTrace();
		}
		super.setUncaughtExceptionHandler(eh);
	}

}
