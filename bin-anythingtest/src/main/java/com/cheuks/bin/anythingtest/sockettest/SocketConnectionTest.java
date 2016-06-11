package com.cheuks.bin.anythingtest.sockettest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketConnectionTest {

	public static void main(String[] args) {

		Socket s = new Socket();
		InputStream in = null;
		OutputStream out;
		long now = System.currentTimeMillis();
		try {
			//			61.92.160.208:1080@SOCKS5
			s.connect(new InetSocketAddress("67.201.33.70", 9100), 1000);
			System.out.println("连接成功:" + (System.currentTimeMillis() - now));
			out = s.getOutputStream();
			out.write("hello".getBytes());
			out.flush();
			in = s.getInputStream();
			int code = 0;
			while ((code = in.read()) != -1) {
				System.err.print((char) code + "(" + code + ")");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != in)
					in.close();
				s.close();
			} catch (IOException e) {
			}
		}
	}

}
