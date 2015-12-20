package com.cheuks.bin.anythingtest.zookeeper.paxos.net.mananger;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.zookeeper.server.FinalRequestProcessor;

import com.cheuks.bin.anythingtest.zookeeper.paxos.net.ConnectionMsg;
import com.cheuks.bin.anythingtest.zookeeper.paxos.net.Logger;

public class SelectorMananger extends AbstractMananger {
	protected Selector selector;
	protected ServerSocketChannel serverSocketChannel;
	protected Set<ServerSocketChannel> serverSocketChannels = new HashSet<ServerSocketChannel>();

	public SelectorMananger(int... port) {
		super();
		try {
			selector = Selector.open();
			for (int i = 0; i < port.length; i++) {
				serverSocketChannels.add(serverSocketChannel = ServerSocketChannel.open());
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
		System.err.println("筛选器启动");
		while (!Thread.interrupted())
			try {
				select();
				// Thread.sleep(10);
			} catch (Exception e) {
				Logger.getDefault().error(this.getClass(), e);
			}
	}

	private AtomicInteger x = new AtomicInteger(0);

	private void select() throws Exception {
		selector.select(20);
		Set<SelectionKey> keys = selector.selectedKeys();
		Iterator<SelectionKey> it = keys.iterator();
		while (it.hasNext()) {
			key = it.next();
			it.remove();
			if (!key.isValid())
				continue;
			else if (key.isAcceptable()) {
				msg = new ConnectionMsg(key).setAction(1).setNo(x.addAndGet(1)).generateId();
				channel = ((ServerSocketChannel) key.channel()).accept();
				channel.configureBlocking(false);
				channel.finishConnect();
				key = channel.register(key.selector(), SelectionKey.OP_READ, msg);
				addHeartBeat(key, msg);
				// System.err.println(x.addAndGet(1));
				continue;
			} else if (((ConnectionMsg) key.attachment()).isSelectable(true)) {
				if (key.isReadable() && getAndSetAction(key, 1, 2)) {
					doTry(ReadDo, ReaderQueue, key);
				} else if (key.isWritable() && getAndSetAction(key, 2, 1)) {
					doTry(WriteDo, WriterQueue, key);
				}
				continue;
			}
		}
	}
}
