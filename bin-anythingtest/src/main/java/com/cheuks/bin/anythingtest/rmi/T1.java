package com.cheuks.bin.anythingtest.rmi;

import java.util.Date;

import com.cheuks.bin.bean.application.ApplicationContext;
import com.cheuks.bin.bean.application.DefaultApplicationContext;
import com.cheuks.bin.net.server.handler.test.ServiceHandlerTestI;

public class T1 {

	public static void main(String[] args) throws Throwable {
		ApplicationContext ac = new DefaultApplicationContext("com.cheuks.bin", false, false, true);
		Date now = new Date();
		for (int i = 10; i-- > 0;) {
			ServiceHandlerTestI ser = ac.getBeans("ServiceHandlerTestI");
			System.err.println("运行时间:" + (new Date().getTime() - now.getTime()) + "ms    " + ser.a());
			System.out.println(ser.mmx().getMethod());
			ser.mmx1();
			now = new Date();
		}
	}

}
