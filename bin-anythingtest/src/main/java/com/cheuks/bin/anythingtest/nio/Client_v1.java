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

	public static void maina(String[] args) throws IOException, InterruptedException {
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
		out.write(ByteBufferUtil.getBuffer("客户端：2-你好吗".getBytes()).array());
		out.flush();
		// countDownLatch.await();
		in.close();

	}

	public static void main(String[] args) {

		final AtomicInteger ax = new AtomicInteger();
		ExecutorService r0 = Executors.newCachedThreadPool();
		ExecutorService r1 = Executors.newCachedThreadPool();
		ExecutorService r2 = Executors.newCachedThreadPool();
		ExecutorService r3 = Executors.newCachedThreadPool();
		int i = 5;
		String ip = "127.0.0.1";//192.168.168.219
		if (args.length == 2) {
			ip = args[0];
			i = Integer.valueOf(args[1]);
		}
		final int[] port = { 10088, 10087, 10089, 10086 };
		final Random r = new Random();
		while ((i -= 4) > 0)
			r0.submit(new Runnable() {
				public void run() {
					try {
						final CountDownLatch countDownLatch = new CountDownLatch(1);
						int obj = port[r.nextInt(2) + 1];
						Socket s = new Socket();
						s.connect(new InetSocketAddress("192.168.168.219", obj));

						// System.err.println("port:" + s.getLocalPort());

						OutputStream out = s.getOutputStream();
						InputStream in = s.getInputStream();
						ax.addAndGet(1);
						for (int i = 0; i < 4; i++) {
							out.write(ByteBufferUtil.getBuffer((ax.get() + ":客户端：" + i + "-你好吗:").getBytes()).array());
							out.flush();
							System.out.println(new String(ByteBufferUtil.getByte(in).toByteArray()) + ":" + s.getLocalPort());
						}
						//						countDownLatch.await();
						Thread.sleep(2000);
						out.close();
						in.close();
					} catch (NumberFormatException e) {
						Logger.getDefault().error(this.getClass(), e);
					} catch (IOException e) {
						Logger.getDefault().error(this.getClass(), e);
					} catch (InterruptedException e) {
						Logger.getDefault().error(this.getClass(), e);
					}
				}
			});
		r1.submit(new Runnable() {
			public void run() {
				try {
					final CountDownLatch countDownLatch = new CountDownLatch(1);
					int obj = port[r.nextInt(2) + 1];
					Socket s = new Socket();
					s.connect(new InetSocketAddress("192.168.168.219", obj));

					// System.err.println("port:" + s.getLocalPort());

					OutputStream out = s.getOutputStream();
					InputStream in = s.getInputStream();
					ax.addAndGet(1);
					for (int i = 0; i < 4; i++) {
						out.write(ByteBufferUtil.getBuffer((ax.get() + ":客户端：" + i + "-你好吗:").getBytes()).array());
						out.flush();
						System.out.println(new String(ByteBufferUtil.getByte(in).toByteArray()) + ":" + s.getLocalPort());
					}
					//						countDownLatch.await();
					Thread.sleep(2000);
					out.close();
					in.close();
				} catch (NumberFormatException e) {
					Logger.getDefault().error(this.getClass(), e);
				} catch (IOException e) {
					Logger.getDefault().error(this.getClass(), e);
				} catch (InterruptedException e) {
					Logger.getDefault().error(this.getClass(), e);
				}
			}
		});
		r2.submit(new Runnable() {
			public void run() {
				try {
					final CountDownLatch countDownLatch = new CountDownLatch(1);
					int obj = port[r.nextInt(2) + 1];
					Socket s = new Socket();
					s.connect(new InetSocketAddress("192.168.168.219", obj));

					// System.err.println("port:" + s.getLocalPort());

					OutputStream out = s.getOutputStream();
					InputStream in = s.getInputStream();
					ax.addAndGet(1);
					for (int i = 0; i < 4; i++) {
						out.write(ByteBufferUtil.getBuffer((ax.get() + ":客户端：" + i + "-你好吗:").getBytes()).array());
						out.flush();
						System.out.println(new String(ByteBufferUtil.getByte(in).toByteArray()) + ":" + s.getLocalPort());
					}
					//						countDownLatch.await();
					Thread.sleep(2000);
					out.close();
					in.close();
				} catch (NumberFormatException e) {
					Logger.getDefault().error(this.getClass(), e);
				} catch (IOException e) {
					Logger.getDefault().error(this.getClass(), e);
				} catch (InterruptedException e) {
					Logger.getDefault().error(this.getClass(), e);
				}
			}
		});
		r3.submit(new Runnable() {
			public void run() {
				try {
					final CountDownLatch countDownLatch = new CountDownLatch(1);
					int obj = port[r.nextInt(2) + 1];
					Socket s = new Socket();
					s.connect(new InetSocketAddress("192.168.168.219", obj));

					// System.err.println("port:" + s.getLocalPort());

					OutputStream out = s.getOutputStream();
					InputStream in = s.getInputStream();
					ax.addAndGet(1);
					for (int i = 0; i < 4; i++) {
						out.write(ByteBufferUtil.getBuffer((ax.get() + ":客户端：" + i + "-你好吗:").getBytes()).array());
						out.flush();
						System.out.println(new String(ByteBufferUtil.getByte(in).toByteArray()) + ":" + s.getLocalPort());
					}
					//						countDownLatch.await();
					Thread.sleep(2000);
					out.close();
					in.close();
				} catch (NumberFormatException e) {
					Logger.getDefault().error(this.getClass(), e);
				} catch (IOException e) {
					Logger.getDefault().error(this.getClass(), e);
				} catch (InterruptedException e) {
					Logger.getDefault().error(this.getClass(), e);
				}
			}
		});
		try {
			Thread.sleep(r.nextInt(10) + 5);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
