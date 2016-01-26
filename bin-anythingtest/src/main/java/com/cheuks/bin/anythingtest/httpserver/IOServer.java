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
		new Thread(new IOServer(80)).start();
	}

	private volatile boolean stop = true;
	private ExecutorService executorService = Executors.newFixedThreadPool(100);
	private ServerSocket ss = null;

	public IOServer(int port) throws IOException {
		super();
		ss = new ServerSocket(port);
	}

	public void run() {
		while (stop) {
			try {
				executorService.submit(new AcceptThread(ss.accept()));
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
				System.err.println(buffer.length);
				//				while ((count = in.read(buffer)) != 0) {
				//					out.write(buffer, 0, count);
				//					if (count < buffer.length)
				//						break;
				//				}
				while ((count = in.read()) != -1) {
					System.out.print((char) count);
					//					System.out.print(count);
				}
				System.out.println("结束");
				System.out.println(new String(outBuffer.toByteArray()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
