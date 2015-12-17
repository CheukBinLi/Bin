package com.cheuks.bin.anythingtest.zookeeper.paxos.net.mananger;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

import com.cheuks.bin.anythingtest.zookeeper.paxos.net.ConnectionMsg;

public class AcceptMananger extends AbstractMananger {

	public AcceptMananger() {
		super();
	}

	@Override
	public void run() {
		System.err.println("侦听器启动");
		while (!Thread.interrupted()) {
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (null != (key = AcceptQueue.poll())) {
				try {
					msg = (ConnectionMsg) key.attachment();
					channel = ((ServerSocketChannel) key.channel()).accept();
					channel.configureBlocking(false);
					channel.finishConnect();
					key = channel.register(key.selector(), SelectionKey.OP_READ, msg);
					//			key.
				} catch (ClosedChannelException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					msg.updateConnectionTime().enableSelectable();
				}
			}
		}
	}

}
