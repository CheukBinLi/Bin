package com.cheuks.bin.anythingtest.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class demo_2_client {

	public static void main(String[] args) throws TException, InterruptedException {
		// // TTransport transport = new TSocket("127.0.0.1", 10091);
		// TTransport transport = new TSocket("127.0.0.1", 8888);
		// TProtocol protocol = new TBinaryProtocol(transport);
		// // HelloWordService.Client client = new HelloWordService.Client(protocol);
		// modelServer.Client client = new modelServer.Client(protocol);
		// transport.open();
		// System.out.println(client.getModel(10010).name);
		// List<model> models = client.getList();
		// for (model m : models)
		// System.out.println(String.format("%d:%s:%s", m.id, m.name, m.remark));
		// transport.close();

		long now = System.currentTimeMillis();
		// for (int i = 0; i < 2; i++) {
		// modelServer.Client modelClient = new modelServer.Client(imp1);
		// // System.out.println(modelClient.getModel(10010).name);
		// // List<model> models = modelClient.getList();
		// // for (model m : models)
		// // System.out.println(String.format("%d:%s:%s", m.id, m.name, m.remark));
		// modelClient.getModel(1);
		// // modelClient.getList();
		// System.err.println(System.currentTimeMillis() - now);
		// now = System.currentTimeMillis();
		// }

		ExecutorService executorService = Executors.newCachedThreadPool();
		final CountDownLatch countDownLatch = new CountDownLatch(400000);
		for (int i = 0; i < 10; i++)
			executorService.submit(new Runnable() {
				public void run() {
					for (int i = 0; i < 4; i++) {
						try {
							TTransport transport = new TSocket("127.0.0.1", 8888);
							TBinaryProtocol protocol = new TBinaryProtocol(transport);
							TMultiplexedProtocol imp1 = new TMultiplexedProtocol(protocol, "modelServer");
							transport.open();
							for (int j = 0; j < 100000; i++) {
								final modelServer.Client modelClient = new modelServer.Client(imp1);
								modelClient.getModel(1);
								countDownLatch.countDown();
							}
							transport.close();
						} catch (notFindModel e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (TException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						countDownLatch.countDown();
					}
				}
			});
		countDownLatch.await();
		System.err.println(System.currentTimeMillis() - now);
	}

}
