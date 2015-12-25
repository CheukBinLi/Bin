package com.cheuks.bin.net.server.event;

import java.lang.reflect.Method;
import java.nio.channels.SelectionKey;
import java.util.concurrent.ConcurrentHashMap;

import com.cheuks.bin.cache.CachePoolFactory;
import com.cheuks.bin.net.server.handler.MessageInfo;
import com.cheuks.bin.net.server.handler.ServiceHandler;
import com.cheuks.bin.net.server.niothread.Attachment;
import com.cheuks.bin.net.util.DefaultSerializImpl;
import com.cheuks.bin.net.util.Serializ;

public class RmiHandleEvent implements HandleEvent {

	protected static Serializ serializ = new DefaultSerializImpl();

	public SelectionKey process(SelectionKey key, final CachePoolFactory cache, final String cacheTag, final ConcurrentHashMap<String, ServiceHandler> SERVICE_HANDLER_MAP) throws Throwable {
		Attachment attachment = (Attachment) key.attachment();
		MessageInfo messageInfo = attachment.getAttachmentX();
		Method m = cache.get4Map(cacheTag, messageInfo.getPath(), messageInfo.getMethod());
		ServiceHandler serviceHandler = SERVICE_HANDLER_MAP.get(messageInfo.getPath());
		Object result = null;
		MessageInfo mi = new MessageInfo();
		if (null != m && null != serviceHandler)
			try {
				result = m.invoke(serviceHandler, messageInfo.getParams());
				mi.setResult(result);
			} catch (IllegalAccessException e) {
				mi.setThrowable(e);
			}
		attachment.setAttachment(mi).registerWrite();
		return key;
	}
}
