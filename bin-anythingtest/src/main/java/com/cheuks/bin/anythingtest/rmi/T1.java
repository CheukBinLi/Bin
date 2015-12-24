package com.cheuks.bin.anythingtest.rmi;

import com.cheuks.bin.bean.application.ApplicationContext;
import com.cheuks.bin.bean.application.DefaultApplicationContext;
import com.cheuks.bin.net.server.handler.test.ServiceHandlerTestI;

public class T1 {

	//	private com.cheuks.bin.net.server.handler.CallMethod callMethod = new com.cheuks.bin.net.server.handler.CallMethod(null,11);
	public static void main(String[] args) throws Throwable {
		ApplicationContext ac = new DefaultApplicationContext("com.cheuks.bin", false, false);
		ServiceHandlerTestI ser = ac.getBeans("ServiceHandlerTestI");
		System.err.println(ser.a());

	}

}
