package com.cheuks.bin.anythingtest.zookeeper.paxos.net;

import java.nio.channels.SelectionKey;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class ConnectionMsg {

	public ConnectionMsg() {
		super();
	}

	final static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

	protected Long getDateTime() {
		return System.currentTimeMillis();
	}

	/**
	 * 是否锁定
	 */
	private AtomicBoolean selectable = new AtomicBoolean(true);
	/***
	 * 心跳时间
	 */
	private AtomicLong connectionTime = new AtomicLong(this.getDateTime());

	private SelectionKey key;
	private String name;

	public boolean isSelectable() {
		return selectable.get() && key.isValid();
	}

	public boolean isSelectable(boolean disable) {
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

	public synchronized ConnectionMsg updateConnectionTime() {
		this.connectionTime.set(getDateTime());
		return this;
	}

	/***
	 * 
	 * @param now 当前时间
	 * @param timeOutInterval 超时间隔
	 * @return
	 */
	public synchronized boolean isConnectionTimeOut(long now, long timeOutInterval) {
		System.out.println("-------" + format.format(new Date(now)) + "---------" + format.format(new Date(this.connectionTime.get())) + "      result:" + (now - this.connectionTime.get() - timeOutInterval));
		return now - this.connectionTime.get() > timeOutInterval;
	}

	public ConnectionMsg setSelectable(AtomicBoolean selectable) {
		this.selectable = selectable;
		return this;
	}

	public ConnectionMsg disableSelectable() {
		this.selectable.set(false);
		return this;
	}

	public ConnectionMsg enableSelectable() {
		this.selectable.set(true);
		return this;
	}

	public ConnectionMsg(SelectionKey key) {
		super();
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public ConnectionMsg setName(String name) {
		this.name = name;
		return this;
	}
}
