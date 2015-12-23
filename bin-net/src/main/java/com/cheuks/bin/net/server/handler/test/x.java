package com.cheuks.bin.net.server.handler.test;

public class x {

	public static void main(String[] args) throws Throwable {

		// Class<?> c = ServiceHandlerTestI.class;
		//
		// Method m = c.getDeclaredMethod("a");
		//
		// ServiceHandler sh = new ServiceHandlerTest();
		//
		// System.out.println(m.invoke(sh, null));
		ServiceHandlerTestI i = new ServiceHandlerTest_net();
		System.err.println(i.a());

	}

}
