package com.cheuks.bin.net.server.handler.test;

import com.cheuks.bin.net.server.handler.CallMethod;

public class ServiceHandlerTest_net extends CallMethod implements ServiceHandlerTestI {

	public String path() {
		return "x/1.0";
	}

	public String a() throws Throwable {
		return (String) call("", "", "");
	}

}
