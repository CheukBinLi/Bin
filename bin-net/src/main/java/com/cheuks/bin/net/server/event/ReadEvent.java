package com.cheuks.bin.net.server.event;

import java.nio.channels.SelectionKey;

public interface ReadEvent {

	public SelectionKey process(final SelectionKey key) throws Throwable;

}
