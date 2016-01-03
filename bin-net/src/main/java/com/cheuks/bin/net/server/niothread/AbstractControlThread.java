package com.cheuks.bin.net.server.niothread;

import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

import com.cheuks.bin.cache.CachePoolFactory;
import com.cheuks.bin.cache.DefaultCachePoolFactory;
import com.cheuks.bin.net.server.event.EventInfo;
import com.cheuks.bin.net.server.handler.ServiceHandler;
import com.cheuks.bin.net.util.DefaultSerializImpl;
import com.cheuks.bin.net.util.Serializ;

public abstract class AbstractControlThread extends Thread {

	protected final static BlockingDeque<SelectionKey> ACCEPT_QUEUE = new LinkedBlockingDeque<SelectionKey>();
	protected final static BlockingDeque<SelectionKey> READER_QUEUE = new LinkedBlockingDeque<SelectionKey>();
	protected final static BlockingDeque<SelectionKey> WRITER_QUEUE = new LinkedBlockingDeque<SelectionKey>();
	protected final static BlockingDeque<SelectionKey> HANDLER_QUEUE = new LinkedBlockingDeque<SelectionKey>();
	protected final static BlockingDeque<ServiceHandler> HANDLER_LIST = new LinkedBlockingDeque<ServiceHandler>();
	protected final static ConcurrentHashMap<String, ServiceHandler> SERVICE_HANDLER_MAP = new ConcurrentHashMap<String, ServiceHandler>();
	protected final static CopyOnWriteArraySet<Release> RELEASE_Queue = new CopyOnWriteArraySet<Release>();
	protected final static BlockingDeque<SelectionKey> RELEASE_LIST = new LinkedBlockingDeque<SelectionKey>();
	protected final static BlockingDeque<ServerSocketChannel> SERVER_LIST = new LinkedBlockingDeque<ServerSocketChannel>();
	protected final static BlockingDeque<Attachment> ATTACHMENT_LIST = new LinkedBlockingDeque<Attachment>();

	protected final static CachePoolFactory cache = DefaultCachePoolFactory.newInstance();
	protected final static String cacheTag = "ServiceHandler";
	protected volatile static Serializ serializ = new DefaultSerializImpl();
	protected final int pollInterval = 2;

	//	// 新
	//	/***
	//	 * @1:channel.hashCode @2:type
	//	 */
	//	protected final static ConcurrentHashMap<Integer, Integer> TYPE_LIST = new ConcurrentHashMap<Integer, Integer>();
	/***
	 * @1:type @2:处理接口
	 */
	protected final static ConcurrentHashMap<Integer, EventInfo> EVENT_LIST = new ConcurrentHashMap<Integer, EventInfo>();

	protected AtomicBoolean shutdown = new AtomicBoolean();

	public AbstractControlThread() {
		super();
	}

	@Override
	public void interrupt() {
		this.shutdown.set(true);
	}

	protected final static void clearAll() {
		ACCEPT_QUEUE.clear();
		READER_QUEUE.clear();
		WRITER_QUEUE.clear();
		RELEASE_Queue.clear();
		RELEASE_LIST.clear();
		RELEASE_LIST.clear();
		HANDLER_QUEUE.clear();
		HANDLER_LIST.clear();
		SERVICE_HANDLER_MAP.clear();
		SERVER_LIST.clear();
		ATTACHMENT_LIST.clear();
		EVENT_LIST.clear();
		//		TYPE_LIST.clear();
	}

	protected static final int ACCEPT = 1, READER = 2, WRITER = 4, RELEASE = 8, HANDLER = 16;

	protected SelectionKey key;
	protected SocketChannel channel;
	protected Attachment attachment;

	public final void setSerializ(Serializ serializ) {
		AbstractControlThread.serializ = serializ;
	}

	public final Attachment getAddition(final SelectionKey key) {
		synchronized (key) {
			Attachment attachment = null;
			if (null != key.attachment())
				attachment = (Attachment) key.attachment();
			return attachment;
		}
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

	public void addServiceHandler(ServiceHandler handler) throws Exception {
		// if (null != (handler = SERVICE_HANDLER_MAP.put(handler.path(),
		// handler))) {
		// throw new Exception("重复的PATH:" + handler.path());
		// }
		HANDLER_LIST.offerLast(handler);
	}

	public synchronized void addEventHandle(EventInfo eventInfos, Integer serviceType) throws Throwable {
		EVENT_LIST.put(serviceType, eventInfos);
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

		case HANDLER:
			HANDLER_QUEUE.offerLast(key);
			break;
		}
	}

}
