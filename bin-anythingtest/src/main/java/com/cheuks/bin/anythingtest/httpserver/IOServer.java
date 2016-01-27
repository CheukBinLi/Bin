package com.cheuks.bin.anythingtest.httpserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IOServer implements Runnable {

	public static void main(String[] args) throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append((char) 10);
		sb.append((char) 13);

		new Thread(new IOServer(80)).start();
	}

	private volatile boolean stop = true;
	private final ExecutorService executorService = Executors.newFixedThreadPool(100);
	private ServerSocket ss = null;

	public IOServer(int port) throws IOException {
		super();
		ss = new ServerSocket(port);

	}

	public void run() {
		Socket s;
		while (stop) {
			try {
				s = ss.accept();
				executorService.submit(new AcceptThread(s));
			} catch (Exception e) {
				e.printStackTrace();
				stop = false;
			}
		}
	}

	class AcceptThread implements Runnable {

		private Socket socket;
		private InputStream in;
		private OutputStream out;

		public AcceptThread(Socket socket) {
			super();
			this.socket = socket;
		}

		public void run() {
			try {
				System.out.println("有客到");
				in = socket.getInputStream();
				byte[] buffer = new byte[512];
				ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();
				int count = 0;
				int prv = 0;
				while ((count = in.read()) != -1) {
					System.out.print((char) count);
					if ('\n' == prv && '\r' == count) {
						break;
					}
					prv = count;
				}
				out = socket.getOutputStream();
				out.write(("HTTP/1.0200OK\n" + "Content-Type:text/html\n" + "Server:myserver\n" + "\n").getBytes());
				out.write("<html><body>aaaaa</body></html>".getBytes());
				System.out.println("结束");
				System.out.println(new String(outBuffer.toByteArray()));
				out.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
