package com.cheuks.bin.net.server.handler.test;

import com.cheuks.bin.net.server.handler.CallMethod;

public class ServiceHandlerTest_net extends CallMethod implements ServiceHandlerTestI {

	public ServiceHandlerTest_net() {
		super("127.0.0.1", 10088);
	}

	public String classID() {
		return "x/1.0";
	}

	public String a() throws Throwable {
		return (String) call(classID(), "java.lang.String:a", null);
	}

}
