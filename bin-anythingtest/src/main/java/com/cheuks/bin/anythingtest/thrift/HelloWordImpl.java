package com.cheuks.bin.anythingtest.thrift;

import org.apache.thrift.TException;

public class HelloWordImpl implements HelloWordService.Iface {

	public String sayHello(String name) throws TException {
		return name+"你好吗？";
	}

}
