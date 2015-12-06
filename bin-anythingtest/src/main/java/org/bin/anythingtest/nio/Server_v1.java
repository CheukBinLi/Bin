package org.bin.anythingtest.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server_v1 {

	private Selector selector;
	private SelectionKey key;
	private ServerSocketChannel server;
	private SocketChannel client;

	public void x() throws IOException {
		selector = Selector.open();
		server = ServerSocketChannel.open();
		server.socket().setSoTimeout(10000);
		server.bind(new InetSocketAddress(10086));
		server.configureBlocking(false);
		server.register(selector, SelectionKey.OP_ACCEPT);

		Set<SelectionKey> keys;
		Iterator<SelectionKey> it;

		while (selector.select() > 0) {
			keys = selector.selectedKeys();
			it = keys.iterator();
			while (it.hasNext()) {
				key = it.next();
				it.remove();
				if (key.isValid()) {
					if (key.isAcceptable()) {
					} else if (key.isReadable()) {
					} else if (key.isWritable()) {
					}
				}
			}
		}

	}
}
