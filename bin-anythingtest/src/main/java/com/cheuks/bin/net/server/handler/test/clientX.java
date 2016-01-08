package com.cheuks.bin.net.server.handler.test;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cheuks.bin.bean.application.ApplicationContext;
import com.cheuks.bin.bean.application.DefaultApplicationContext;

public class clientX {

	public static void main(String[] args) throws Throwable {
		final ApplicationContext ac = new DefaultApplicationContext("com.cheuks.bin", false, false, true);
		ExecutorService executorService = Executors.newCachedThreadPool();
		for (int i = 100; i-- > 0;) {
			Thread.sleep(0);
			executorService.submit(new Runnable() {
				public void run() {
					try {
						Date now = new Date();
						ServiceHandlerTestI ser = ac.getBeans("ServiceHandlerTestI");
						//						System.err.println("运行时间:" + (new Date().getTime() - now.getTime()) + "ms " + ser.a());
						//						System.out.println(ser.mmx().getMethod());
						//						System.err.println(ser.a("哈哈哈哈哈::"));
						//						Thread.sleep(30000);
						//						System.err.println(ser.a("哈佬::", 1));
						ser.a();
						ser.mmx().getMethod();
						ser.a("哈哈哈哈哈::");
						ser.a("哈佬::", 1);
						System.err.println("运行时间:" + (new Date().getTime() - now.getTime()) + "ms ");
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			});
			Thread.sleep(80);
		}
	}

	//	static {
	//		com.cheuks.bin.net.server.handler.test.ServiceHandlerTest2$MC_IMPL o = null;
	//		o = (com.cheuks.bin.net.server.handler.test.ServiceHandlerTest2$MC_IMPL) super.clone();
	//		o.callMethod = (com.cheuks.bin.net.server.handler.test.ServiceHandlerTest2$MC_IMPL) o.com.cheuks.bin.net.server.handler.CallMethod.clone();
	//		return o;
	//	}

}