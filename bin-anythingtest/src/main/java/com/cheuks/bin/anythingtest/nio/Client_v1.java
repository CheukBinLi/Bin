package com.cheuks.bin.anythingtest.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client_v1 {

	public void mainX() throws IOException, InterruptedException {
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		Socket s = new Socket();
		s.connect(new InetSocketAddress("127.0.0.1", 10088));
		// s.connect(new InetSocketAddress("127.0.0.1", 10087));
		// s.connect(new InetSocketAddress("127.0.0.1", 10089));

		OutputStream out = s.getOutputStream();
		out.write(ByteBufferUtil.getBuffer("客户端：1-你好吗".getBytes()).array());
		out.flush();
		InputStream in = s.getInputStream();
		System.out.println(new String(ByteBufferUtil.getByte(in).toByteArray()));
		// out.write(ByteBufferUtil.getBuffer("客户端：2-你好吗".getBytes()).array());
		// out.flush();
		// countDownLatch.await();
		in.close();

	}

	public static void main(String[] args) {

		ExecutorService e = Executors.newCachedThreadPool();
		int i = 100;
		while (i-- > 0) {
			e.submit(new Runnable() {
				public void run() {
					try {
						final CountDownLatch countDownLatch = new CountDownLatch(1);
						Socket s = new Socket();
						s.connect(new InetSocketAddress("127.0.0.1", 10088));
						// s.connect(new InetSocketAddress("127.0.0.1", 10087));
						// s.connect(new InetSocketAddress("127.0.0.1", 10089));

						OutputStream out = s.getOutputStream();
						out.write(ByteBufferUtil.getBuffer("客户端：1-你好吗".getBytes()).array());
						out.flush();
						InputStream in = s.getInputStream();
						System.out.println(new String(ByteBufferUtil.getByte(in).toByteArray()));
						// out.write(ByteBufferUtil.getBuffer("客户端：2-你好吗".getBytes()).array());
						// out.flush();
//						countDownLatch.await();
						in.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

}
