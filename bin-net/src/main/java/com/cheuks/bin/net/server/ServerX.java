package com.cheuks.bin.net.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cheuks.bin.util.Logger;

public class ServerX {
	public static void main(String[] args) {
		Logger.getDefault().setErrorWrite(false).setInfoWrite(true);
		ExecutorService mananger = Executors.newFixedThreadPool(10);
		mananger.execute(new SelectorThread(2, 10086, 10087, 10088, 10089));
		mananger.execute(new ReleaseListThread());
		mananger.execute(new ReleaseQueueThread(5000));
		mananger.execute(new AttachmentListThread(5));
		mananger.execute(new ReaderThread());
		mananger.execute(new WriterThread());
	}
}
