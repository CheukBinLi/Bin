package com.cheuks.bin.anythingtest.zookeeper.paxos.net.mananger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

import com.cheuks.bin.anythingtest.zookeeper.paxos.net.ConnectionMsg;

public class SelectorMananger extends AbstractMananger {

	protected Selector selector;
	protected ServerSocketChannel serverSocketChannel;

	public SelectorMananger(int port) {
		super();
		try {
			selector = Selector.open();
			serverSocketChannel = ServerSocketChannel.open();
			// serverSocketChannel.socket().setSoTimeout(10000);
			serverSocketChannel.socket().setReuseAddress(true);
			serverSocketChannel.bind(new InetSocketAddress(port));
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (ClosedChannelException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		System.err.println("选择器启动");
		while (!Thread.interrupted())
			try {
				select();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	private void select() throws Exception {
		selector.select(50);
		Iterator<SelectionKey> it = selector.selectedKeys().iterator();
		while (it.hasNext()) {
			key = it.next();
			it.remove();
			if (!key.isValid())
				continue;
			if (null == key.attachment()) {
				key.attach(new ConnectionMsg(key));
//				AcceptQueue.offer(key);
//				continue;
			}
			if (((ConnectionMsg) key.attachment()).isSelectable(true)) {
				ScorterQueue.offer(key);
			}
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
	}
}
