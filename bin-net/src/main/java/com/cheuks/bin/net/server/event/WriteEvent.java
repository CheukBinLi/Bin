package com.cheuks.bin.net.server.event;

import java.nio.channels.SelectionKey;

public interface WriteEvent {
	public SelectionKey process(final SelectionKey key) throws Throwable;
}
