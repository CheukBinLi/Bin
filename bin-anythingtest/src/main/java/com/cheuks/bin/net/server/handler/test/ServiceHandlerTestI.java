package com.cheuks.bin.net.server.handler.test;

import com.cheuks.bin.annotation.Register;
import com.cheuks.bin.annotation.RmiClient;
import com.cheuks.bin.net.server.handler.ServiceHandler;

@Register
@RmiClient(path = "127.0.0.1:10088", classID = "x/1.0")
public interface ServiceHandlerTestI extends ServiceHandler {
	public String a() throws Throwable;
}
