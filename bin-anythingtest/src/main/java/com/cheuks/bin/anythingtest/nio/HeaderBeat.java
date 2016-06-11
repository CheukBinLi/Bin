package com.cheuks.bin.anythingtest.nio;

import com.cheuks.bin.net.util.ByteBufferUtil2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class HeaderBeat {
	private static Socket s;

	public static void main(String[] args) throws NumberFormatException, IOException, InterruptedException {
		s = new Socket();
		s.setSoTimeout(60000);
		s.connect(new InetSocketAddress("127.0.0.1", 10088));
		InputStream in = s.getInputStream();
		OutputStream out = s.getOutputStream();
		for (int i = 0; i < 4; i++) {
			out.write(ByteBufferUtil2.getBuffer(("").getBytes()).array());
			out.flush();
			System.out.println(new String(ByteBufferUtil2.getByte(in).toByteArray()) + ":" + s.getLocalPort());
			Thread.sleep(2000);
		}
		// countDownLatch.await();
		out.close();
		in.close();
	}
}
