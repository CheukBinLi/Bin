package com.cheuks.bin.anythingtest.thrift;

import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class demo_2_client {

	public static void main(String[] args) throws TException {
		//		//		TTransport transport = new TSocket("127.0.0.1", 10091);
		//		TTransport transport = new TSocket("127.0.0.1", 8888);
		//		TProtocol protocol = new TBinaryProtocol(transport);
		//		//		HelloWordService.Client client = new HelloWordService.Client(protocol);
		//		modelServer.Client client = new modelServer.Client(protocol);
		//		transport.open();
		//		System.out.println(client.getModel(10010).name);
		//		List<model> models = client.getList();
		//		for (model m : models)
		//			System.out.println(String.format("%d:%s:%s", m.id, m.name, m.remark));
		//		transport.close();

		TTransport transport = new TSocket("127.0.0.1", 8888);
		TBinaryProtocol protocol = new TBinaryProtocol(transport);
		TMultiplexedProtocol imp1 = new TMultiplexedProtocol(protocol, "modelServer");
		modelServer.Client modelClient = new modelServer.Client(imp1);
		transport.open();
		System.out.println(modelClient.getModel(10010).name);
		List<model> models = modelClient.getList();
		for (model m : models)
			System.out.println(String.format("%d:%s:%s", m.id, m.name, m.remark));
		transport.close();
	}

}
