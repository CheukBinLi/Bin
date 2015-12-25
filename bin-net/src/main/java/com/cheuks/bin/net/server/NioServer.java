package com.cheuks.bin.net.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cheuks.bin.net.server.event.EventInfo;
import com.cheuks.bin.net.server.handler.ServiceHandler;
import com.cheuks.bin.net.server.niothread.AttachmentListThread;
import com.cheuks.bin.net.server.niothread.HandlerListThread;
import com.cheuks.bin.net.server.niothread.HandlerQueueThread;
import com.cheuks.bin.net.server.niothread.ReaderThreadMananger;
import com.cheuks.bin.net.server.niothread.ReleaseListThread;
import com.cheuks.bin.net.server.niothread.ReleaseQueueThread;
import com.cheuks.bin.net.server.niothread.SelectorThread;
import com.cheuks.bin.net.server.niothread.WriterThreadMananger;

public class NioServer implements Server {

	ExecutorService executorService;
	ArrayList<Integer[]> ports = new ArrayList<Integer[]>();
	SelectorThread selectorThread;

	long timeOut = 60000;
	Long refreshInterval = 2L;
	int attachmentQueue = 2;
	int maxConnection = 2000;

	private static final Server newInstance = new NioServer();

	public static final Server newInstance() {
		return newInstance;
	}

	private NioServer() {
	}

	public Server start(Integer maxConnection) {
		if (null == executorService || executorService.isShutdown()) {
			executorService = Executors.newFixedThreadPool(10);
			executorService.submit(selectorThread = new SelectorThread(maxConnection, this.refreshInterval, this.ports));
			executorService.submit(new ReleaseQueueThread(this.timeOut));
			executorService.submit(new AttachmentListThread(5));
			executorService.submit(new ReleaseListThread());
			executorService.submit(new ReaderThreadMananger());
			executorService.submit(new WriterThreadMananger());
			executorService.submit(new HandlerListThread());
			executorService.submit(new HandlerQueueThread());
			//			// 处理测试
			//			try {
			//				selectorThread.addServiceHandler(new ServiceHandlerTest());
			//			} catch (Exception e) {
			//				e.printStackTrace();
			//			}
		}
		return this;
	}

	public Server stop() {
		if (null != executorService && !executorService.isShutdown()) {
			executorService.shutdown();
		}
		return this;
	}

	public Server addService(Integer port, Integer serviceType) throws IOException {
		selectorThread.addListener(port, serviceType);
		return this;
	}

	public Server addHandler(ServiceHandler... handler) throws Exception {
		for (int i = 0, len = handler.length; i < len; i++)
			selectorThread.addServiceHandler(handler[i]);
		return this;
	}

	public Server setTimeOut(long timeOut) throws Throwable {
		this.timeOut = timeOut;
		return this;
	}

	public Server start() throws Throwable {
		return this.start(this.maxConnection);
	}

	public Server addEventHandle(EventInfo eventInfos, Integer serviceType) throws Throwable {
		selectorThread.addEventHandle(eventInfos, serviceType);
		return this;
	}

}
