package com.cheuks.bin.net.server.handler.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.cheuks.bin.net.server.handler.ServiceHandler;

public class x {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Class<?> c = ServiceHandlerTestI.class;

		Method m = c.getDeclaredMethod("a");

		ServiceHandler sh = new ServiceHandlerTest();
		
		System.out.println(m.invoke(sh, null));

	}

}
