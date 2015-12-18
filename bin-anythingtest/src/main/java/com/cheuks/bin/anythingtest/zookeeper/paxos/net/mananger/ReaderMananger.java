package com.cheuks.bin.anythingtest.zookeeper.paxos.net.mananger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

import com.cheuks.bin.anythingtest.zookeeper.paxos.net.ConnectionMsg;
import com.cheuks.bin.anythingtest.zookeeper.paxos.net.Logger;
import com.cheuks.bin.net.util.ByteBufferUtil;

public class ReaderMananger extends AbstractMananger {

	@Override
	public void run() {
		// System.err.println("接收器启动");
		while (!Thread.interrupted()) {
			//			try {
			//				Thread.sleep(sleep);
			//			} catch (InterruptedException e1) {
			//				e1.printStackTrace();
			//			}
			try {
				if (null != (key = ReaderQueue.poll(pollWait, TimeUnit.MICROSECONDS)) && key.isValid()) {
					msg = (ConnectionMsg) key.attachment();
					try {
						channel = (SocketChannel) key.channel();
						if (!channel.isConnected()) {
							key.cancel();
							continue;
						}
						ByteArrayOutputStream out = ByteBufferUtil.getByte(channel);
						//						System.err.println(new String(out.toByteArray()));
						key = channel.register(key.selector(), SelectionKey.OP_WRITE, msg.updateConnectionTime().enableSelectable());
						// key.
					} finally {
					}
				}
			} catch (Exception e) {
				key.cancel();
				Logger.getDefault().info(getClass(), e);
			}
		}
	}

}
