package com.cheuks.bin.net.server;

import com.cheuks.bin.net.server.handler.ServiceHandler;

public interface Server {

	Server start() throws Throwable;

	Server start(Integer maxConnection) throws Throwable;

	Server stop() throws Throwable;

	Server setTimeOut(long timeOut) throws Throwable;

	Server addService(Integer... port) throws Throwable;

	Server addHandler(ServiceHandler... handler) throws Throwable;

}
