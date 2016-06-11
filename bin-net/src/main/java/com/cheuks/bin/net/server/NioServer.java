package com.cheuks.bin.net.server;

import com.cheuks.bin.net.server.event.*;
import com.cheuks.bin.net.server.handler.ServiceHandler;
import com.cheuks.bin.net.server.niothread.*;
import com.cheuks.bin.net.util.ByteBufferUtil.DataPacket;
import com.cheuks.bin.net.util.Serializ;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioServer implements Server {

	ExecutorService executorService;
	ArrayList<Integer[]> ports = new ArrayList<Integer[]>();
	volatile SelectorThread selectorThread;
	volatile WriterThreadMananger writerThreadMananger;
	volatile HandlerQueueThread handlerQueueThread;

	Integer refreshInterval = 50;
	int attachmentQueue = 2;
	int maxConnection = 2000;

	private static final Server newInstance = new NioServer();

	public static final Server newInstance() {
		return newInstance;
	}

	private NioServer() {
	}

	public Server start(Integer maxConnection, long timeOut) {
		if (null == executorService || executorService.isShutdown()) {
			executorService = Executors.newFixedThreadPool(10);
			selectorThread = new SelectorThread(maxConnection, this.refreshInterval, this.ports);
			executorService.submit(selectorThread);
			executorService.submit(new ReleaseQueueThread(timeOut));
			executorService.submit(new AttachmentListThread(5));
			executorService.submit(new ReleaseListThread());
			executorService.submit(new ReaderThreadMananger());
			executorService.submit(writerThreadMananger = new WriterThreadMananger());
			executorService.submit(new HandlerListThread());
			executorService.submit(handlerQueueThread = new HandlerQueueThread());
			//初始化服务
			try {
				addEventHandle(new EventInfo(new RmiWriteEvent(), new RmiHandleEvent()), DataPacket.SERVICE_TYPE_RMI);
				addEventHandle(new EventInfo(new HeartbeatWriteEvent(), new HeartbeatHandleEvent()), DataPacket.SERVICE_TYPE_HEARTBEAT);
				addEventHandle(new EventInfo(new MessageWriteEvent(), new MessageHandleEvent()), DataPacket.SERVICE_TYPE_MESSAGE);
			} catch (Throwable e) {
				e.printStackTrace();
			}
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

	public Server start() throws Throwable {
		return this.start(this.maxConnection, 60000);
	}

	public Server addEventHandle(EventInfo eventInfos, Integer serviceType) throws Throwable {
		selectorThread.addEventHandle(eventInfos, serviceType);
		return this;
	}

	public Server setSerializ(Serializ serializ) {
		//		System.err.println(serializ.getSuperclass());
		//				selectorThread.setSerializ(serializ);
		handlerQueueThread.setSerializ(serializ.getClass());
		writerThreadMananger.setSerializ(serializ.getClass());
		return this;
	}

}
