package com.cheuks.bin.anythingtest.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client_v1 {

	public static void main(String[] args) throws IOException {

		Socket s = new Socket();
		s.connect(new InetSocketAddress("127.0.0.1", 10086));

		OutputStream out = s.getOutputStream();
		out.write(ByteBufferUtil.getBuffer("客户端：1-你好吗".getBytes()).array());
		out.flush();
		InputStream in = s.getInputStream();
		System.out.println(new String(ByteBufferUtil.getByte(in).toByteArray()));
		in.close();

	}

}
