package com.cheuks.bin.base.web.util;

import com.google.gson.Gson;

public abstract class Message {

	public static final int SUCCESS = 1;
	public static final int FAIL = 0;

	/***
	 * 1：成功
	 */
	private int code;
	private String msg;

	public String toJson() {
		return new Gson().toJson(this);
	}

	public Message(boolean isSuccess) {
		super();
		this.code = isSuccess ? SUCCESS : FAIL;
	}

	public Message(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public Message setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public Message setMsg(String msg) {
		this.msg = msg;
		return this;
	}
}
