package com.cheuks.bin.net.server.event;

public class EventInfo {

	private WriteEvent writeEvent;
	private HandleEvent handleEvent;

	public EventInfo(WriteEvent writeEvent, HandleEvent handleEvent) {
		super();
		this.writeEvent = writeEvent;
		this.handleEvent = handleEvent;
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
