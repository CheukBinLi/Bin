package com.cheuks.bin.net.server.handler.test;

import com.cheuks.bin.net.server.handler.MessageInfo;
import com.cheuks.bin.net.server.handler.ServiceHandler;
import com.cheuks.bin.net.util.Serializ;

/***
 * 远程实现
 * 
 * @author Ben
 *
 */
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

	public String a(String haha) throws Throwable {
		return haha + "叼嗱星!";
	}

	public String a(String haha, int a) throws Throwable {
		// TODO Auto-generated method stub
		return haha + a;
	}

	public ServiceHandler setSerializ(Serializ serializ) {
		return null;
	}

	public Integer mmx3() throws Throwable {
		// TODO Auto-generated method stub
		return 0;
	}
}
