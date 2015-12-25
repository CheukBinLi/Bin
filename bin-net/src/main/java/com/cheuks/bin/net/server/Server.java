package com.cheuks.bin.net.server;

import com.cheuks.bin.net.server.event.EventInfo;
import com.cheuks.bin.net.server.handler.ServiceHandler;

public interface Server {

	public static final int SERVICE_TYPE_RMI = 1, SERVICE_TYPE_MESSAGE = 2;

	Server start() throws Throwable;

	Server start(Integer maxConnection) throws Throwable;

	Server stop() throws Throwable;

	Server setTimeOut(long timeOut) throws Throwable;

	Server addService(Integer port, Integer serviceType) throws Throwable;

	Server addHandler(ServiceHandler... handler) throws Throwable;

	Server addEventHandle(EventInfo eventInfos, Integer serviceType) throws Throwable;

}
