package com.cheuks.bin.anythingtest.zookeeper.paxos.net;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ConnectionMsg {

	// final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd
	// HH:mm:ss");

	protected Long getDateTime() {
		return System.currentTimeMillis();
	}

	/**
	 * 是否锁定
	 */
	private final AtomicBoolean selectable = new AtomicBoolean(true);
	/***
	 * 心跳时间
	 */
	private final AtomicLong connectionTime = new AtomicLong(this.getDateTime());

	private final AtomicInteger action = new AtomicInteger(0);

	private SelectionKey key;
	private String id;
	private String name;
	private Integer no;

	public boolean isSelectable() {
		return selectable.get() && key.isValid();
	}

	public synchronized boolean isSelectable(final boolean disable) {
		try {
			return selectable.get() && key.isValid();
		} finally {
			if (disable)
				this.disableSelectable();
		}
	}

	public long getConnectionTime() {
		return connectionTime.get();
	}

	public ConnectionMsg updateConnectionTime() {
		this.connectionTime.set(getDateTime());
		return this;
	}

	/***
	 * 
	 * @param now
	 *            当前时间
	 * @param timeOutInterval
	 *            超时间隔
	 * @return
	 */
	public synchronized boolean isConnectionTimeOut(final long now, final long timeOutInterval) {
		return now - this.getConnectionTime() > timeOutInterval;
	}

	public synchronized ConnectionMsg disableSelectable() {
		this.selectable.set(false);
		return this;
	}

	public synchronized ConnectionMsg enableSelectable() {
		this.selectable.set(true);
		return this;
	}

	public ConnectionMsg(SelectionKey key) {
		super();
		// System.err.println("流言");
		this.key = key;
		if (key.channel() instanceof SocketChannel)
			try {
				this.name = ((SocketChannel) key.channel()).getRemoteAddress().toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public String getName() {
		return name;
	}

	public synchronized ConnectionMsg generateId() {
		this.id = UUID.randomUUID().toString();
		return this;
	}

	public ConnectionMsg setName(String name) {
		this.name = name;
		return this;
	}

	public String getId() {
		return id;
	}

	public int getAction() {
		return action.get();
	}

	public boolean getAndSetAction(final int currentAction, final int nextAction) {
		synchronized (this.action) {
			if (currentAction == this.action.get()) {
				this.action.set(nextAction);
				return true;
			}
			return false;
		}
	}

	public ConnectionMsg setAction(int action) {
		this.action.set(action);
		return this;
	}

	public Integer getNo() {
		return no;
	}

	public ConnectionMsg setNo(Integer no) {
		this.no = no;
		return this;
	}

}
