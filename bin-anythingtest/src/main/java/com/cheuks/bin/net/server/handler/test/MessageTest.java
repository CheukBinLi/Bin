package com.cheuks.bin.net.server.handler.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.cheuks.bin.net.util.ByteBufferUtil;

public class MessageTest {

	public static void main(String[] args) throws IOException {
		Socket s = new Socket();
		s.connect(new InetSocketAddress("127.0.0.1", 10087));
		OutputStream out = s.getOutputStream();
		out.write(ByteBufferUtil.getBytes("Message测试"));
		out.flush();
		InputStream in = s.getInputStream();
		System.err.println(new String(ByteBufferUtil.getByte(in).toByteArray()));
		in.close();
		out.close();
		s.close();
	}

}
