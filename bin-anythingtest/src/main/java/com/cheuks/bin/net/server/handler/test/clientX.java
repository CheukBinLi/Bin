package com.cheuks.bin.net.server.handler.test;

import java.util.Date;

import com.cheuks.bin.bean.application.ApplicationContext;
import com.cheuks.bin.bean.application.DefaultApplicationContext;

public class clientX {

	public static void main(String[] args) throws Throwable {
		ApplicationContext ac = new DefaultApplicationContext("com.cheuks.bin", false, false, true);
		Date now = new Date();
		for (int i = 3; i-- > 0;) {
			// ServiceHandlerTestI ser = ac.getBeans("ServiceHandlerTestI");
			ServiceHandlerTest2 ser = ac.getBeans("ServiceHandlerTest2");
			System.err.println("运行时间:" + (new Date().getTime() - now.getTime()) + "ms " + ser.a());
			System.out.println(ser.mmx().getMethod());
			System.err.println(ser.a("哈哈哈哈哈:" + i + ":"));
			System.err.println(ser.a("哈佬:" + i + ":", i));
			now = new Date();
		}
	}

}
