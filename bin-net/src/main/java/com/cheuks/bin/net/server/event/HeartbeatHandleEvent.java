package com.cheuks.bin.net.server.event;

import com.cheuks.bin.cache.CachePoolFactory;
import com.cheuks.bin.net.server.handler.ServiceHandler;
import com.cheuks.bin.net.server.niothread.Attachment;
import com.cheuks.bin.net.util.DefaultSerializImpl;
import com.cheuks.bin.net.util.Serializ;

import java.nio.channels.SelectionKey;
import java.util.concurrent.ConcurrentHashMap;

public class HeartbeatHandleEvent implements HandleEvent {

	protected static Serializ serializ = new DefaultSerializImpl();

	public SelectionKey process(SelectionKey key, Serializ serializ, final CachePoolFactory cache, final String cacheTag, final ConcurrentHashMap<String, ServiceHandler> SERVICE_HANDLER_MAP) throws Throwable {
		Attachment attachment = (Attachment) key.attachment();
		attachment.getAttachment().setData(null);
		attachment.registerWrite();
		return key;
	}
}
