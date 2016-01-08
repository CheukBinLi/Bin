package com.cheuks.bin.net.server.handler.test;

import java.nio.channels.SelectionKey;
import java.util.concurrent.ConcurrentHashMap;

import com.cheuks.bin.cache.CachePoolFactory;
import com.cheuks.bin.net.server.event.HandleEvent;
import com.cheuks.bin.net.server.handler.ServiceHandler;
import com.cheuks.bin.net.server.niothread.Attachment;
import com.cheuks.bin.net.util.Serializ;
import com.cheuks.bin.util.Logger;
import com.cheuks.bin.net.util.ByteBufferUtil.DataPacket;

public class LogHandleEvent implements HandleEvent {

	public SelectionKey process(SelectionKey key, Serializ serializ, CachePoolFactory cache, String cacheTag, ConcurrentHashMap<String, ServiceHandler> SERVICE_HANDLER_MAP) throws Throwable {
		Attachment a = (Attachment) key.attachment();
		DataPacket dp = a.getAttachment();
		Logger.getDefault().info("rmi_log", new String(dp.getData()));
		dp.setData(null);
		a.registerWrite();
		return key;
	}

}
