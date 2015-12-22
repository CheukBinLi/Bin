package com.cheuks.bin.net.server.handler;

import java.io.Serializable;

public class MessageInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String path;
	private String method;
	private Object[] params;
	private Object result;

	public String getPath() {
		return path;
	}

	public MessageInfo setPath(String path) {
		this.path = path;
		return this;
	}

	public String getMethod() {
		return method;
	}

	public MessageInfo setMethod(String method) {
		this.method = method;
		return this;
	}

	public Object[] getParams() {
		return params;
	}

	public MessageInfo setParams(Object[] params) {
		this.params = params;
		return this;
	}

	public Object getResult() {
		return result;
	}

	public MessageInfo setResult(Object result) {
		this.result = result;
		return this;
	}

}
