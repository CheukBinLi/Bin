package com.cheuks.bin.net.server.event;

import java.nio.channels.SelectionKey;

import com.cheuks.bin.net.util.Serializ;

public interface WriteEvent {
	public SelectionKey process(final SelectionKey key, Serializ serializ) throws Throwable;
}
