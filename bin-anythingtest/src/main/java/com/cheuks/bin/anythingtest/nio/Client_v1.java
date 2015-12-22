package com.cheuks.bin.anythingtest.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.cheuks.bin.anythingtest.zookeeper.paxos.net.Logger;
import com.cheuks.bin.net.util.ByteBufferUtil;

public class Client_v1 {

	public static void main(String[] args) {

		final AtomicInteger ax = new AtomicInteger();
		ExecutorService r0 = Executors.newCachedThreadPool();
		ExecutorService r1 = Executors.newCachedThreadPool();
		ExecutorService r2 = Executors.newCachedThreadPool();
		ExecutorService r3 = Executors.newCachedThreadPool();
		ExecutorService r4 = Executors.newCachedThreadPool();
		ExecutorService r5 = Executors.newCachedThreadPool();
		ExecutorService r6 = Executors.newCachedThreadPool();
		ExecutorService r7 = Executors.newCachedThreadPool();
		ExecutorService r8 = Executors.newCachedThreadPool();
		ExecutorService r9 = Executors.newCachedThreadPool();
		int i = 500;
		//				final StringBuffer ip = new StringBuffer("192.168.168.150");// 192.168.168.219
		final StringBuffer ip = new StringBuffer("127.0.0.1");// 192.168.168.219
		if (args.length == 2) {
			ip.setLength(0);
			ip.append(args[0]);
			i = Integer.valueOf(args[1]);
		}
		final int[] port = { 10088, 10087, 10089, 10086 };
		final Random r = new Random();
		int x;
		while (i-- > 0)
			try {
				x = r.nextInt(2) + 1;
				// x = 0;
				r0.submit(new connectionTest(port[x], ip.toString(), ax.get()));
				r1.submit(new connectionTest(port[x], ip.toString(), ax.get()));
				r2.submit(new connectionTest(port[x], ip.toString(), ax.get()));
				r3.submit(new connectionTest(port[x], ip.toString(), ax.get()));
				r4.submit(new connectionTest(port[x], ip.toString(), ax.get()));
				r5.submit(new connectionTest(port[x], ip.toString(), ax.get()));
				r6.submit(new connectionTest(port[x], ip.toString(), ax.get()));
				r7.submit(new connectionTest(port[x], ip.toString(), ax.get()));
				r8.submit(new connectionTest(port[x], ip.toString(), ax.get()));
				r9.submit(new connectionTest(port[x], ip.toString(), ax.get()));

				// Thread.sleep(r.nextInt(50) + 10);
				Thread.sleep(50);
				// break;
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}

	static class connectionTest implements Runnable {

		private int port;
		private String ip;
		private final int ax;
		private int timeOut = 6000;

		public connectionTest(int port, String ip, int ax) {
			super();
			this.port = port;
			this.ip = ip;
			this.ax = ax;
		}

		public void run() {
			try {
				final CountDownLatch countDownLatch = new CountDownLatch(1);
				Socket s = new Socket();
				s.setSoTimeout(60000);
				s.connect(new InetSocketAddress(ip, port));
				InputStream in = s.getInputStream();
				OutputStream out = s.getOutputStream();
				for (int i = 0; i < 4; i++) {
					out.write(ByteBufferUtil.getBuffer((ax + ":客户端：" + i + "-你好吗:").getBytes()).array());
					out.flush();
					System.out.println(new String(ByteBufferUtil.getByte(in).toByteArray()) + ":" + s.getLocalPort());
				}
				// countDownLatch.await();
				Thread.sleep(2000);
				out.close();
				in.close();
			} catch (NumberFormatException e) {
				Logger.getDefault().error(this.getClass(), e);
			} catch (IOException e) {
				//				Logger.getDefault().error(this.getClass(), e);
				//				System.err.println("  " + port + " : " + ax);
				e.printStackTrace();
			} catch (InterruptedException e) {
				Logger.getDefault().error(this.getClass(), e);
			}
		}
	}
}
