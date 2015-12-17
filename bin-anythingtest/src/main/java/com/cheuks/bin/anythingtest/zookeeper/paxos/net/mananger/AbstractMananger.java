package com.cheuks.bin.anythingtest.zookeeper.paxos.net.mananger;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

import com.cheuks.bin.anythingtest.zookeeper.paxos.net.ConnectionMsg;
import com.cheuks.bin.anythingtest.zookeeper.paxos.net.Logger;

public class AbstractMananger extends Thread {

	protected static final ReentrantLock lock = new ReentrantLock();
	protected static long sleep = 10;
	protected static final BlockingQueue<SelectionKey> AcceptQueue = new LinkedBlockingQueue<SelectionKey>();
	protected static final BlockingQueue<SelectionKey> ReaderQueue = new LinkedBlockingQueue<SelectionKey>();
	protected static final BlockingQueue<SelectionKey> WriterQueue = new LinkedBlockingQueue<SelectionKey>();
	protected static final BlockingQueue<SelectionKey> ScorterQueue = new LinkedBlockingQueue<SelectionKey>();
	protected static final ConcurrentHashMap<String, SelectionKey> HeartBeat = new ConcurrentHashMap<String, SelectionKey>();
	protected SelectionKey key;
	protected ConnectionMsg msg;
	protected SocketChannel channel;

	public void addHeartBeat(SelectionKey key) {
		try {
			Object o;
			if ((o = key.channel()) instanceof SocketChannel) {
				HeartBeat.put(((SocketChannel) o).getRemoteAddress().toString(), key);
			}
		} catch (IOException e) {
			Logger.getDefault().error(this.getClass(), e);
		}
	}
}
