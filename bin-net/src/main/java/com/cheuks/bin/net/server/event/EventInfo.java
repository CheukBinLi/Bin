package com.cheuks.bin.net.server.event;

public class EventInfo {

	private ReadEvent readEvent;
	private WriteEvent writeEvent;
	private HandleEvent handleEvent;

	public EventInfo(ReadEvent readEvent, WriteEvent writeEvent, HandleEvent handleEvent) {
		super();
		this.readEvent = readEvent;
		this.writeEvent = writeEvent;
		this.handleEvent = handleEvent;
	}

	public ReadEvent getReadEvent() {
		return readEvent;
	}

	public EventInfo setReadEvent(ReadEvent readEvent) {
		this.readEvent = readEvent;
		return this;
	}

	public WriteEvent getWriteEvent() {
		return writeEvent;
	}

	public EventInfo setWriteEvent(WriteEvent writeEvent) {
		this.writeEvent = writeEvent;
		return this;
	}

	public HandleEvent getHandleEvent() {
		return handleEvent;
	}

	public EventInfo setHandleEvent(HandleEvent handleEvent) {
		this.handleEvent = handleEvent;
		return this;
	}

}
