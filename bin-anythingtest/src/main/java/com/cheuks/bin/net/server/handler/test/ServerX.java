package com.cheuks.bin.net.server.handler.test;

import com.cheuks.bin.net.server.NioServer;
import com.cheuks.bin.net.server.Server;

public class ServerX {
	public static void main(String[] args) throws Throwable {
		Server server = NioServer.newInstance();
		server.start(2000, 20000);
		server.addService(10088, Server.SERVICE_TYPE_RMI).addService(10087, Server.SERVICE_TYPE_MESSAGE);
		server.addHandler(new ServiceHandlerTest());
		//		server.addEventHandle(new EventInfo(new RmiWriteEvent(), new RmiHandleEvent()), Server.SERVICE_TYPE_RMI);
		//		server.addEventHandle(new EventInfo(new MessageWriteEvent(), new MessageHandleEvent()), Server.SERVICE_TYPE_MESSAGE);
	}
}
