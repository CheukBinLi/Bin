package com.cheuks.bin.anythingtest.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class Client_v1 {

	public static void main(String[] args) throws IOException, InterruptedException {
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		Socket s = new Socket();
		// s.connect(new InetSocketAddress("127.0.0.1", 10086));
		s.connect(new InetSocketAddress("127.0.0.1", 10088));

		OutputStream out = s.getOutputStream();
		out.write(ByteBufferUtil.getBuffer("客户端：1-你好吗".getBytes()).array());
		out.flush();
		InputStream in = s.getInputStream();
		System.out.println(new String(ByteBufferUtil.getByte(in).toByteArray()));
		//		out.write(ByteBufferUtil.getBuffer("客户端：2-你好吗".getBytes()).array());
		//		out.flush();
//		countDownLatch.await();
		in.close();

	}

}
