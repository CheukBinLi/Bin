package com.cheuks.bin.net.server.test;

import com.cheuks.bin.net.server.NioServer;
import com.cheuks.bin.net.server.Server;
import com.cheuks.bin.net.server.event.EventInfo;
import com.cheuks.bin.net.server.event.RmiHandleEvent;
import com.cheuks.bin.net.server.event.RmiReadEvent;
import com.cheuks.bin.net.server.event.RmiWriteEvent;

public class ServerX {
	public static void main(String[] args) throws Throwable {
		Server server = NioServer.newInstance();
		server.start(2000, 10000);
		server.addService(10088, Server.SERVICE_TYPE_RMI);
		server.addHandler(new ServiceHandlerTest());
		server.addEventHandle(new EventInfo(new RmiReadEvent(), new RmiWriteEvent(), new RmiHandleEvent()), Server.SERVICE_TYPE_RMI);
	}
}
