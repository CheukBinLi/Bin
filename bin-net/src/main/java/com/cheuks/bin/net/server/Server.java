package com.cheuks.bin.net.server;

import com.cheuks.bin.net.server.handler.ServiceHandler;

public interface Server {

	void start();

	void stop();

	void addService(int... port);

	void addHandler(ServiceHandler... handler);

}
