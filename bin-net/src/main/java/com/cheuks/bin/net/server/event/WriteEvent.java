package com.cheuks.bin.net.server.event;

import com.cheuks.bin.net.util.Serializ;

import java.nio.channels.SelectionKey;

public interface WriteEvent {
	public SelectionKey process(final SelectionKey key, Serializ serializ) throws Throwable;
}
