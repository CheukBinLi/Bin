package com.cheuks.bin.net.server.niothread;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.cheuks.bin.net.server.handler.MessageInfo;

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
	private MessageInfo ReceiveInfo;

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

	public MessageInfo getReceiveInfo() {
		return ReceiveInfo;
	}

	public Attachment setReceiveInfo(MessageInfo receiveInfo) {
		ReceiveInfo = receiveInfo;
		return this;
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
			lock();
			setActionType(nextAction);
			return this;
		}
	}

	public Attachment updateHeartBeatAndSetActionType(int nextAction) {
		synchronized (actionType) {
			setActionType(nextAction);
			updateHeartBeat();
			return this;
		}
	}

	public SelectionKey unLockAndUpdateHeartBeat(final SocketChannel channel, final SelectionKey key, int ops, final MessageInfo receiveInfo) throws ClosedChannelException {
		synchronized (lock) {
			unLock();
			updateHeartBeat();
			this.ReceiveInfo = receiveInfo;
			return channel.register(key.selector(), ops, this);
		}
	}

	public Attachment unLockAndUpdateHeartBeat() {
		synchronized (lock) {
			unLock();
			updateHeartBeat();
			return this;
		}
	}

	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/***
	 * 
	 * @param now
	 *            当前时间
	 * @param timeOutInterval
	 *            超时间隔
	 * @return
	 */
	public synchronized boolean isConnectionTimeOut(final long now, final long timeOutInterval) {
		// System.out.println(String.format("now:%s- this.getConnectionTime():%s
		// > timeOutInterval", sdf.format(new Date(now)), sdf.format(new
		// Date(this.getConnectionTime()))));
		return now - this.getConnectionTime() > timeOutInterval;
	}

}
