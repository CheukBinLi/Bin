package com.cheuks.bin.net.server;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Attachment {

	public final static int AT_READING = 1;
	public final static int AT_WRITING = 2;

	final long getCurrentTime() {
		return System.currentTimeMillis();
	}

	private final String id;
	private final AtomicBoolean lock = new AtomicBoolean(false);
	private final AtomicLong connectionTime = new AtomicLong();
	private final AtomicInteger actionType = new AtomicInteger();

	public Attachment updateHeartBeat() {
		this.connectionTime.set(getCurrentTime());
		return this;
	}

	public Attachment(String id) {
		super();
		this.id = id;
	}

	public boolean isLock() {
		return lock.get();
	}

	public Attachment lock() {
		lock.set(true);
		return this;
	}

	public Attachment unLock() {
		lock.set(false);
		return this;
	}

	public long getConnectionTime() {
		return connectionTime.get();
	}

	public String getId() {
		return id;
	}

	public boolean isCurrentActionType(int action) {
		return actionType.get() == action;
	}

	public Attachment setActionType(int action) {
		actionType.set(action);
		return this;
	}

	public boolean lockSetActionTypeAnd(int currentAction, int nextAction) {
		synchronized (lock) {
			if (isCurrentActionType(currentAction)) {
				lock.set(true);
				actionType.set(nextAction);
				return true;
			}
			return false;
		}
	}

	public Attachment lockAndSetActionType(int nextAction) {
		synchronized (lock) {
			lock.set(true);
			actionType.set(nextAction);
			return this;
		}
	}

	public Attachment updateHeartBeatAndSetActionType(int nextAction) {
		synchronized (actionType) {
			actionType.set(nextAction);
			updateHeartBeat();
			return this;
		}
	}

	public SelectionKey unLockAndUpdateHeartBeat(final SocketChannel channel, final SelectionKey key, int ops) throws ClosedChannelException {
		synchronized (lock) {
			lock.set(false);
			updateHeartBeat();
			return channel.register(key.selector(), ops, this);
		}
	}

	public Attachment unLockAndUpdateHeartBeat() {
		synchronized (lock) {
			lock.set(false);
			updateHeartBeat();
			return this;
		}
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

}
