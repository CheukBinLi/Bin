package com.cheuks.bin.net.server.event;

import com.cheuks.bin.cache.CachePoolFactory;
import com.cheuks.bin.net.server.handler.ServiceHandler;
import com.cheuks.bin.net.util.Serializ;

import java.nio.channels.SelectionKey;
import java.util.concurrent.ConcurrentHashMap;

public interface HandleEvent {
	public SelectionKey process(final SelectionKey key, Serializ serializ, final CachePoolFactory cache, String cacheTag, final ConcurrentHashMap<String, ServiceHandler> SERVICE_HANDLER_MAP) throws Throwable;
}
