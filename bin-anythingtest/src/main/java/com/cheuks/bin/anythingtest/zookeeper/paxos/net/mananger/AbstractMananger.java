package com.cheuks.bin.anythingtest.zookeeper.paxos.net.mananger;

import java.io.IOException;
import java.net.SocketOption;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

import com.cheuks.bin.anythingtest.zookeeper.paxos.net.ConnectionMsg;
import com.cheuks.bin.anythingtest.zookeeper.paxos.net.Logger;
import com.cheuks.bin.anythingtest.zookeeper.paxos.net.Release;

public class AbstractMananger extends Thread {

	protected static final ReentrantLock lock = new ReentrantLock();
	protected static long pollWait = 10;
	protected static final BlockingQueue<SelectionKey> AcceptQueue = new LinkedBlockingQueue<SelectionKey>();
	protected static final BlockingQueue<SelectionKey> ReaderQueue = new LinkedBlockingQueue<SelectionKey>();
	protected static final CopyOnWriteArraySet<String> ReadDo = new CopyOnWriteArraySet<String>();
	protected static final BlockingQueue<SelectionKey> WriterQueue = new LinkedBlockingQueue<SelectionKey>();
	protected static final CopyOnWriteArraySet<String> WriteDo = new CopyOnWriteArraySet<String>();
	protected static final BlockingQueue<SelectionKey> ScorterQueue = new LinkedBlockingQueue<SelectionKey>();
	protected static final CopyOnWriteArraySet<Release> HeartBeat = new CopyOnWriteArraySet<Release>();

	protected SelectionKey key;
	protected ConnectionMsg msg;
	protected SocketChannel channel;

	public void addHeartBeat(final SelectionKey key, final ConnectionMsg msg) {
		try {
			// Object o;
			if (null != key /*
							 * && (o = key.channel()) instanceof SocketChannel
							 */) {
				key.attach(msg.enableSelectable().updateConnectionTime());
				HeartBeat.add(new Release(key));
			}
		} catch (IOException e) {
			Logger.getDefault().error(this.getClass(), e);
		}
	}

	public ConnectionMsg getConnectionMsg(final SelectionKey key) {
		Object o = key.attachment();
		ConnectionMsg msg = null;
		if (null != o) {
			msg = (ConnectionMsg) key.attachment();
		}
		return msg;
	}

	public void doTry(final CopyOnWriteArraySet<String> isDo, final BlockingQueue<SelectionKey> actionQueue, final SelectionKey key) {
		// msg = getConnectionMsg(key);
		// if (null == msg || isDo.contains(msg.getId())) {
		// return;
		// }
		actionQueue.offer(key);
		addHeartBeat(key, msg);
		// isDo.add(msg.getId());
	}

	public void doFinish(final CopyOnWriteArraySet<String> doQueue, final ConnectionMsg msg) {
		// if (null == msg || doQueue.contains(msg.getId())) {
		// return;
		// }
		// doQueue.remove(msg.getId());
		addHeartBeat(key, msg);
	}

	public synchronized void doFinish(final CopyOnWriteArraySet<String> doQueue, final SelectionKey key) {
		msg = getConnectionMsg(key);
		doFinish(doQueue, msg);
	}

	public synchronized boolean getAndSetAction(final SelectionKey key, final int currentAction, final int nextAction) {
		return getConnectionMsg(key).getAndSetAction(currentAction, nextAction);
	}

}
