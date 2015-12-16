package com.cheuks.bin.anythingtest.zookeeper.paxos.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.ReentrantLock;

import com.cheuks.bin.anythingtest.nio.ByteBufferUtil;

public class Server extends Thread {

	protected Selector selector;
	private SelectionKey selectionKey;
	protected ServerSocketChannel serverSocketChannel;
	private int port;
	private String channelName;
	private ExecutorService executorService = Executors.newCachedThreadPool();
	private static final ConcurrentSkipListSet<SelectionKey> connectionQueue = new ConcurrentSkipListSet<SelectionKey>();
	private static final ConcurrentSkipListSet<SelectionKey> RWQueue = new ConcurrentSkipListSet<SelectionKey>();

	public static void main(String[] args) {
		new Server(10088, "单机").start();
	}

	public Server(int port, String channelName) {
		super();
		this.port = port;
		this.channelName = channelName;

		try {
			selector = Selector.open();
			serverSocketChannel = ServerSocketChannel.open();
			// serverSocketChannel.socket().setSoTimeout(10000);
			serverSocketChannel.socket().setReuseAddress(true);
			serverSocketChannel.bind(new InetSocketAddress(port));
			serverSocketChannel.configureBlocking(false);
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (ClosedChannelException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void select() throws Exception {
		selector.select();
		Iterator<SelectionKey> it = selector.selectedKeys().iterator();
		while (it.hasNext()) {
			selectionKey = it.next();
			it.remove();
			if (!selectionKey.isValid())
				continue;
			// if (!connectionQueue.contains(selectionKey) &&
			// selectionKey.isAcceptable()) {
			// connectionQueue.add(selectionKey);
			// executorService.execute(new Accept(selectionKey));
			// } else if (!RWQueue.contains(selectionKey)) {
			// RWQueue.add(selectionKey);
			// executorService.execute(new ReadWrite(selectionKey));
			// }
			selectionKey.cancel();
			selector.selectNow();
			executorService.execute(new Accept(selector, selectionKey));

		}
	}

	public void run() {
		try {
			System.err.println("服务启动");
			while (!Thread.interrupted())
				select();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setUncaughtExceptionHandler(UncaughtExceptionHandler eh) {
		try {
			Selector.open().close();
		} catch (IOException e) {
			// 日志
			e.printStackTrace();
		}
		super.setUncaughtExceptionHandler(eh);
	}

	static class Accept implements Runnable {

		private final SelectionKey key;
		private SocketChannel client;
		private Selector selector;

		public Accept(Selector selector, SelectionKey key) {
			super();
			this.selector = selector;
			this.key = key;
		}

		public void run() {
			try {
				// System.err.println(key);
				// if (key.isAcceptable()) {
				System.err.println("连接");
				client = ((ServerSocketChannel) key.channel()).accept();
				System.err.println("连接");
				client.configureBlocking(false);
				System.err.println("连接");
				client.finishConnect();
				System.err.println("连接");
				client.register(selector, SelectionKey.OP_READ);
				System.err.println("连接111");
				key.selector().wakeup();
				System.err.println("连接111");
				// }
				if (key.isReadable()) {
					client = (SocketChannel) key.channel();
					ByteArrayOutputStream out = ByteBufferUtil.getByte(client);
					System.err.println(new String(out.toByteArray()));
					client.register(key.selector(), SelectionKey.OP_WRITE);
					System.err.println("写");
				} else if (key.isWritable()) {
					client = (SocketChannel) key.channel();
					client.write(ByteBufferUtil.getBuffer("服务器：结束对话".getBytes()));
					key.cancel();
					client.close();
					System.err.println("结束");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// connectionQueue.remove(key);
			}
		}
	}

	static class ReadWrite implements Runnable {

		private SelectionKey key;
		private Selector selector;
		private SocketChannel client;

		public ReadWrite(Selector selector, SelectionKey key) {
			super();
			this.selector = selector;
			this.key = key;
		}

		public void run() {
			try {
				if (key.isReadable()) {
					client = (SocketChannel) key.channel();
					ByteArrayOutputStream out = ByteBufferUtil.getByte(client);
					System.err.println(new String(out.toByteArray()));
					client.register(selector, SelectionKey.OP_WRITE);
					System.err.println("写");
				} else if (key.isWritable()) {
					client = (SocketChannel) key.channel();
					client.write(ByteBufferUtil.getBuffer("服务器：结束对话".getBytes()));
					key.cancel();
					client.close();
					System.err.println("结束");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				RWQueue.remove(key);
			}
		}
	}
}
