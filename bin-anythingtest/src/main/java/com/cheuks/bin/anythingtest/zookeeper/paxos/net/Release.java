package com.cheuks.bin.anythingtest.zookeeper.paxos.net;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class Release {

	private String name;
	private SelectionKey key;

	public Release(SelectionKey key) throws IOException {
		super();
		this.key = key;
		if (key.channel() instanceof SocketChannel)
			this.name = ((SocketChannel) key.channel()).getRemoteAddress().toString();
	}

	@Override
	public int hashCode() {
		System.err.println(this.getMsg().getId().hashCode());
		return this.getMsg().getId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Release)
			return this.getMsg().getId().equals(((Release) obj).getMsg().getId());
		return false;
	}

	public String getName() {
		return name;
	}

	public Release setName(String name) {
		this.name = name;
		return this;
	}

	public SelectionKey getKey() {
		return key;
	}

	public Release setKey(SelectionKey key) {
		this.key = key;
		return this;
	}

	public ConnectionMsg getMsg() {
		try {
			Object o = this.key.attachment();
			if (null != o)
				return (ConnectionMsg) o;
		} catch (Exception e) {
			Logger.getDefault().info(getClass(), e);
		}
		return null;
	}

}
