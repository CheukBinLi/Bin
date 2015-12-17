package com.cheuks.bin.anythingtest.zookeeper.paxos.net.mananger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import com.cheuks.bin.anythingtest.zookeeper.paxos.net.ConnectionMsg;
import com.cheuks.bin.net.util.ByteBufferUtil;

public class WritererMananger extends AbstractMananger {

	@Override
	public void run() {
		System.err.println("发送器启动");
		while (!Thread.interrupted()) {
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (null != (key = WriterQueue.poll())) {
				try {
					msg = (ConnectionMsg) key.attachment();
					channel = (SocketChannel) key.channel();
					channel.write(ByteBufferUtil.getBuffer("服务回复：".getBytes()));
					key = channel.register(key.selector(), SelectionKey.OP_CONNECT, msg);
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
