package com.cheuks.bin.anythingtest.thrift;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

public class demo_2_server {

	public static void main(String[] args) throws TTransportException {
		//		TProcessor process = new HelloWordService.Processor(new HelloWordImpl());
		//		modelServer.Processor process = new modelServer.Processor(new modelServerImpl());
		//		TServerSocket serverSocket = new TServerSocket(10091);
		//		TServer.Args targs = new TServer.Args(serverSocket);
		//		targs.processor(process);
		//		targs.protocolFactory(new TBinaryProtocol.Factory());
		//		TServer server = new TSimpleServer(targs);
		//		server.serve();

		TMultiplexedProcessor processors = new TMultiplexedProcessor();
		processors.registerProcessor("HelloWordService", new HelloWordService.Processor(new HelloWordImpl()));
		processors.registerProcessor("modelServer", new modelServer.Processor(new modelServerImpl()));
		TServerTransport transport = new TServerSocket(8888);
		TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(transport).processor(processors));
		server.serve();
	}

}
