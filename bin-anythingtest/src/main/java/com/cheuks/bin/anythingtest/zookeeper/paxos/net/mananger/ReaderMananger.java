package com.cheuks.bin.anythingtest.zookeeper.paxos.net.mananger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import com.cheuks.bin.anythingtest.zookeeper.paxos.net.ConnectionMsg;
import com.cheuks.bin.net.util.ByteBufferUtil;

public class ReaderMananger extends AbstractMananger {

	@Override
	public void run() {
		System.err.println("接收器启动");
		while (!Thread.interrupted()) {
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (null != (key = ReaderQueue.poll()) && key.isValid()) {
				try {
					msg = (ConnectionMsg) key.attachment();
					channel = (SocketChannel) key.channel();
					if (!channel.isConnected()) {
						key.cancel();
						continue;
					}
					ByteArrayOutputStream out = ByteBufferUtil.getByte(channel);
					System.err.println(new String(out.toByteArray()));
					key = channel.register(key.selector(), SelectionKey.OP_WRITE, msg);
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
