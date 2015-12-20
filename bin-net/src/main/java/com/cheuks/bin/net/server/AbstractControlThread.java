package com.cheuks.bin.net.server;

import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingDeque;

public abstract class AbstractControlThread extends Thread {

	protected final static BlockingDeque<SelectionKey> ACCEPT_QUEUE = new LinkedBlockingDeque<SelectionKey>();
	protected final static BlockingDeque<SelectionKey> READER_QUEUE = new LinkedBlockingDeque<SelectionKey>();
	protected final static BlockingDeque<SelectionKey> WRITER_QUEUE = new LinkedBlockingDeque<SelectionKey>();
	protected final static CopyOnWriteArraySet<Release> RELEASE_Queue = new CopyOnWriteArraySet<Release>();
	protected final static BlockingDeque<SelectionKey> RELEASE_LIST = new LinkedBlockingDeque<SelectionKey>();
	protected final static BlockingDeque<ServerSocketChannel> SERVER_LIST = new LinkedBlockingDeque<ServerSocketChannel>();
	protected final static BlockingDeque<Attachment> ATTACHMENT_LIST = new LinkedBlockingDeque<Attachment>();
	protected final static Object SyncAttachmentThread = new Object();

	protected final static void clearAll() {
		ACCEPT_QUEUE.clear();
		READER_QUEUE.clear();
		WRITER_QUEUE.clear();
		RELEASE_Queue.clear();
		RELEASE_LIST.clear();
		RELEASE_LIST.clear();
	}

	protected static final int ACCEPT = 1, READER = 2, WRITER = 4, RELEASE = 8;

	protected SelectionKey key;
	protected SocketChannel channel;
	protected Attachment attachment;

	public final Attachment getAddition(final SelectionKey key) {
		Attachment attachment = null;
		if (null != key.attachment())
			attachment = (Attachment) key.attachment();
		return attachment;
	}

	public Attachment createAttachment() {
		synchronized (ATTACHMENT_LIST) {
			try {
				return ATTACHMENT_LIST.pop();
			} finally {
				ATTACHMENT_LIST.notify();
			}
		}
	}

	public void tryDo(int queue, final SelectionKey key) {
		switch (queue) {
		case ACCEPT:
			ACCEPT_QUEUE.offerLast(key);

			break;
		case READER:
			READER_QUEUE.offerLast(key);

			break;
		case WRITER:
			WRITER_QUEUE.offerLast(key);

			break;
		case RELEASE:
			RELEASE_LIST.offerLast(key);
			break;
		}
	}

}
