package com.cheuks.bin.net.server;

import java.nio.channels.SelectionKey;

public class Release {

	private String id;
	private Attachment attachment;
	private SelectionKey key;

	@Override
	public int hashCode() {
		return this.getId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return this.getId().equals(((Release) obj).id);
	}

	public Release(String id, Attachment attachment, SelectionKey key) {
		super();
		this.id = id;
		this.attachment = attachment;
		this.key = key;
	}

	public Release() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public SelectionKey getKey() {
		return key;
	}

}
