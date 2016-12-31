package com.cheuks.bin.net.server.handler.test;

import com.cheuks.bin.annotation.Register;
import com.cheuks.bin.annotation.RmiClient;
import com.cheuks.bin.net.server.handler.MessageInfo;
import com.cheuks.bin.net.server.handler.ServiceHandler;

@Register
@RmiClient(path = "127.0.0.1:10088", classID = "x/1.0")
public interface ServiceHandlerTestI extends ServiceHandler {
	public String a() throws Throwable;

	public String a(String haha) throws Throwable;

	public MessageInfo mmx() throws Throwable;

	public Integer mmx3() throws Throwable;

	public void mmx1() throws Throwable;

	public String a(String haha, int a) throws Throwable;
}
