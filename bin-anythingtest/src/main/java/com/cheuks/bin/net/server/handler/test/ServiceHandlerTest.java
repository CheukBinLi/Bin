package com.cheuks.bin.net.server.handler.test;

import com.cheuks.bin.net.server.handler.MessageInfo;

public class ServiceHandlerTest implements ServiceHandlerTestI {

	public String classID() {
		return "x/1.0";
	}

	public String a() {
		return "aaaaaaaaaaaaaaaasdflkasdklfsdbfwrlkwsdfjsdklfjl";
	}

	public MessageInfo mmx() throws Throwable {
		MessageInfo mi = new MessageInfo();
		mi.setMethod("xxxxxxxxxxxx");
		mi.setPath("123");
		return mi;
	}

	public void mmx1() throws Throwable {
		System.out.println("mmx1");
	}

}
