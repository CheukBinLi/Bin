package com.cheuks.bin.anythingtest.zookeeper.paxos.net.mananger;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.cheuks.bin.anythingtest.zookeeper.paxos.net.ConnectionMsg;
import com.cheuks.bin.anythingtest.zookeeper.paxos.net.Logger;
import com.cheuks.bin.net.util.ByteBufferUtil;

public class WritererMananger extends AbstractMananger {
	private static AtomicInteger a = new AtomicInteger(0);

	@Override
	public void run() {
		// System.err.println("发送器启动");
		while (!Thread.interrupted()) {
			try {
				if (null != (key = WriterQueue.poll(pollWait, TimeUnit.MICROSECONDS))) {
					msg = (ConnectionMsg) key.attachment();
					try {
						channel = (SocketChannel) key.channel();
						channel.write(ByteBufferUtil.getBuffer(("服务回复：" + a.addAndGet(1)).getBytes()));
						key = channel.register(key.selector(), SelectionKey.OP_CONNECT, msg.updateConnectionTime().enableSelectable());
						// key.
					} finally {

					}
				}
			} catch (Exception e) {
				key.cancel();
				Logger.getDefault().info(getClass(), e);
			} finally {
			}
		}
	}

}
