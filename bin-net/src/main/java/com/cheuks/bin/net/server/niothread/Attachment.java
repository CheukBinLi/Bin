package com.cheuks.bin.net.server.niothread;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Attachment {

	public final static int AT_READING = 1;
	public final static int AT_HANDLING = 2;
	public final static int AT_WRITING = 3;

	final long getCurrentTime() {
		return System.currentTimeMillis();
	}

	private final String id;
	private final AtomicBoolean lock = new AtomicBoolean(false);
	private final AtomicInteger register = new AtomicInteger();
	private final AtomicLong connectionTime = new AtomicLong();
	private final AtomicInteger actionType = new AtomicInteger();
	private int serviceCode;
	private Object attachment;

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

	public int getRegister() {
		return register.get();
	}

	public Attachment setRegister(int ops) {
		register.set(ops);
		return this;
	}

	public Object getAttachment() {
		return attachment;
	}

	public <T> T getAttachmentX() {
		if (null != attachment)
			return (T) attachment;
		return null;
	}

	public Attachment setAttachment(Object attachment) {
		this.attachment = attachment;
		return this;
	}

	public boolean isCurrentActionType(int action) {
		return actionType.get() == action;
	}

	public Attachment setActionType(int action) {
		actionType.set(action);
		return this;
	}

	public int getServiceCode() {
		return serviceCode;
	}

	public Attachment setServiceCode(int serviceCode) {
		this.serviceCode = serviceCode;
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

	public Attachment updateHeartBeatAndSetActionTypeAndServiceCode(int nextAction, SelectionKey key) {
		synchronized (actionType) {
			this.serviceCode = key.channel().hashCode();
			setActionType(nextAction);
			updateHeartBeat();
			return this;
		}
	}

	public SelectionKey unLockAndUpdateHeartBeat(final SelectionKey key, int ops, final Object messageInfo) throws ClosedChannelException {
		synchronized (lock) {
			unLock();
			updateHeartBeat();
			this.attachment = messageInfo;
			key.selector().wakeup();
			return key.channel().register(key.selector(), ops, this);
		}
	}

	public Attachment unLockAndUpdateHeartBeat() {
		synchronized (lock) {
			unLock();
			updateHeartBeat();
			return this;
		}
	}

	public Attachment registerWrite() {
		this.register.set(SelectionKey.OP_WRITE);
		return this;
	}

	public Attachment registerRead() {
		this.register.set(SelectionKey.OP_READ);
		return this;
	}

	public Attachment registerConnect() {
		this.register.set(SelectionKey.OP_CONNECT);
		return this;
	}

	public void registerClose(SelectionKey key) throws IOException {
		key.channel().close();
		key.channel();
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
		//		System.out.println(String.format("now:%s- this.getConnectionTime():%s> timeOutInterval", sdf.format(new Date(now)), sdf.format(new Date(this.getConnectionTime()))));
		return now - this.getConnectionTime() > timeOutInterval;
	}

	//	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
