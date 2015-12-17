package com.cheuks.bin.anythingtest.zookeeper.paxos.net.mananger;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import com.cheuks.bin.anythingtest.zookeeper.paxos.net.ConnectionMsg;
import com.cheuks.bin.anythingtest.zookeeper.paxos.net.Logger;

public class SelectorMananger extends AbstractMananger {
	protected Selector selector;
	protected ServerSocketChannel serverSocketChannel;
	protected Set<ServerSocketChannel> serverSocketChannels = new HashSet<ServerSocketChannel>();
	private ReentrantLock lock = new ReentrantLock();

	public SelectorMananger(int... port) {
		super();
		try {
			selector = Selector.open();
			for (int i = 0; i < port.length; i++) {
				serverSocketChannels.add(serverSocketChannel = ServerSocketChannel.open());
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
		// System.err.println("筛选器启动");
		while (!Thread.interrupted())
			try {
				select();
			} catch (Exception e) {
				Logger.getDefault().error(this.getClass(), e);
			}
	}

	private void select() throws Exception {
		selector.select(5);
		Iterator<SelectionKey> it = selector.selectedKeys().iterator();
		while (it.hasNext()) {
			key = it.next();
			it.remove();
			if (!key.isValid())
				continue;
			if (null == key.attachment()) {
				key.attach(new ConnectionMsg(key).disableSelectable());
				AcceptQueue.offer(key);
				continue;
			}
			if (((ConnectionMsg) key.attachment()).isSelectable(true)) {
				ScorterQueue.offer(key);
			}
			Thread.sleep(sleep);
		}
	}
}
