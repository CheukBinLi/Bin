package com.cheuks.bin.net.server.event;

import java.nio.channels.SelectionKey;
import java.util.concurrent.ConcurrentHashMap;

import com.cheuks.bin.cache.CachePoolFactory;
import com.cheuks.bin.net.server.handler.ServiceHandler;

public interface HandleEvent {
	public SelectionKey process(final SelectionKey key, final CachePoolFactory cache, final String cacheTag, final ConcurrentHashMap<String, ServiceHandler> SERVICE_HANDLER_MAP) throws Throwable;
}
