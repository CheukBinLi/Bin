package com.cheuks.bin.anythingtest.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

public class demo_1_server {

	public static void main(String[] args) throws TTransportException {
		TProcessor process = new HelloWordService.Processor(new HelloWordImpl());
		TServerSocket serverSocket = new TServerSocket(10099);
		TServer.Args targs = new TServer.Args(serverSocket);
		targs.processor(process);
		targs.protocolFactory(new TBinaryProtocol.Factory());
		TServer server = new TSimpleServer(targs);
		server.serve();
	}

}
