package com.cheuks.bin.anythingtest.zookeeper.paxos.net;

import java.nio.channels.SelectionKey;

public class Release {

	private String name;
	private SelectionKey key;

	public Release(String name, SelectionKey key) {
		super();
		this.name = name;
		this.key = key;
	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return name.equals(obj);
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
