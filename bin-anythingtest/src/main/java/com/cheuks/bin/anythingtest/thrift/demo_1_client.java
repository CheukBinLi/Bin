package com.cheuks.bin.anythingtest.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class demo_1_client {

	public static void main(String[] args) throws TException {
//		TTransport transport = new TSocket("127.0.0.1", 10099);
		TTransport transport = new TSocket("127.0.0.1", 8888);
		TProtocol protocol = new TBinaryProtocol(transport);
		HelloWordService.Client client = new HelloWordService.Client(protocol);
		transport.open();
		System.out.println(client.sayHello("叼拿星"));
		transport.close();
	}

}
