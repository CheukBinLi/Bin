package com.cheuks.bin.anythingtest.nio;

import com.cheuks.bin.net.util.ByteBufferUtil2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executors;

public class Server_v1 implements Runnable {

	private Selector selector;
	private SelectionKey key;
	private ServerSocketChannel server;
	private SocketChannel client;

	public void run() {
		try {
			selector = Selector.open();
			server = ServerSocketChannel.open();
			server.socket().setSoTimeout(10000);
			server.bind(new InetSocketAddress(10086));
			server.configureBlocking(false);
			server.register(selector, SelectionKey.OP_ACCEPT);

			Set<SelectionKey> keys;
			Iterator<SelectionKey> it;
			System.err.println("开始侦听");
			while (selector.select() > 0) {
				keys = selector.selectedKeys();
				it = keys.iterator();
				while (it.hasNext()) {
					key = it.next();
					it.remove();
					if (key.isValid()) {
						if (key.isAcceptable()) {
							client = ((ServerSocketChannel) key.channel()).accept();
							client.configureBlocking(false);
							client.finishConnect();
							client.register(key.selector(), SelectionKey.OP_READ);
						} else if (key.isReadable()) {
							client = (SocketChannel) key.channel();
							ByteArrayOutputStream out = ByteBufferUtil2.getByte(client);
							// System.err.println(new
							// String(out.toByteArray()));
							client.register(key.selector(), SelectionKey.OP_WRITE);
						} else if (key.isWritable()) {
							client = (SocketChannel) key.channel();
							client.write(ByteBufferUtil2.getBuffer("服务器：结束对话".getBytes()));
							key.cancel();
							client.close();
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		Executors.newCachedThreadPool().execute(new Server_v1());
	}
}
