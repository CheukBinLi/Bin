package com.cheuks.bin.net.server.handler.test;

import com.cheuks.bin.net.server.NioServer;
import com.cheuks.bin.net.server.Server;
import com.cheuks.bin.net.server.event.EventInfo;
import com.cheuks.bin.net.server.event.MessageHandleEvent;
import com.cheuks.bin.net.server.event.MessageWriteEvent;
import com.cheuks.bin.net.server.event.RmiHandleEvent;
import com.cheuks.bin.net.server.event.RmiWriteEvent;

public class ServerX {
	public static void main(String[] args) throws Throwable {
		Server server = NioServer.newInstance();
		server.start(2000, 600000);
		server.addService(10088, Server.SERVICE_TYPE_RMI).addService(10087, Server.SERVICE_TYPE_MESSAGE);
		server.addHandler(new ServiceHandlerTest());
		//		server.addEventHandle(new EventInfo(new RmiWriteEvent(), new RmiHandleEvent()), Server.SERVICE_TYPE_RMI);
		//		server.addEventHandle(new EventInfo(new MessageWriteEvent(), new MessageHandleEvent()), Server.SERVICE_TYPE_MESSAGE);
	}
}
