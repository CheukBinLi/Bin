package com.cheuks.bin.anythingtest.netty.privatestack;

import java.util.HashMap;
import java.util.Map;

public class Message {

	private Header header;
	private Object body;

	static public class Header {

		private int type;
		private int version = 0xabcdef01;
		private long length;
		private long sessionID;
		private Map<String, Object> attchment = new HashMap<String, Object>();

		public int getType() {
			return type;
		}

		public Header setType(int type) {
			this.type = type;
			return this;
		}

		public int getVersion() {
			return version;
		}

		public Header setVersion(int version) {
			this.version = version;
			return this;
		}

		public long getLength() {
			return length;
		}

		public Header setLength(long length) {
			this.length = length;
			return this;
		}

		public long getSessionID() {
			return sessionID;
		}

		public Header setSessionID(long sessionID) {
			this.sessionID = sessionID;
			return this;
		}

		public Map<String, Object> getAttchment() {
			return attchment;
		}

		public Header setAttchment(Map<String, Object> attchment) {
			this.attchment = attchment;
			return this;
		}
	}

	public Header getHeader() {
		return header;
	}

	public Message setHeader(Header header) {
		this.header = header;
		return this;
	}

	public <T> T getBody() {
		return (T) body;
	}

	public Message setBody(Object body) {
		this.body = body;
		return this;
	}

	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Message(Header header, Object body) {
		super();
		this.header = header;
		this.body = body;
	}

}
