package com.cheuks.bin.net.server.handler.test;

import com.cheuks.bin.annotation.Register;
import com.cheuks.bin.annotation.RmiClient;
import com.cheuks.bin.net.server.handler.MessageInfo;

/***
 * 远程实现
 * 
 * @author Ben
 *
 */

@Register
@RmiClient(path = "127.0.0.1:10088", classID = "x/1.0")
public class ServiceHandlerTest2 implements ServiceHandlerTestI {

	public String classID() {
		return "";
	}

	public String a() {
		return null;
	}

	public MessageInfo mmx() throws Throwable {
		return null;
	}

	public void mmx1() throws Throwable {
	}

	public String a(String haha) throws Throwable {
		return "";
	}

	public String a(String haha, int a) throws Throwable {
		return "";
	}

}
