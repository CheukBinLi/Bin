package com.cheuks.bin.anythingtest.zookeeper.paxos.net.mananger;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.cheuks.bin.anythingtest.zookeeper.paxos.net.ConnectionMsg;
import com.cheuks.bin.net.util.ByteBufferUtil;

public class WritererMananger extends AbstractMananger {
	private static AtomicInteger a = new AtomicInteger(0);

	@Override
	public void run() {
		// System.err.println("发送器启动");
		while (!Thread.interrupted()) {
			try {
				if (null != (key = WriterQueue.poll(5, TimeUnit.MICROSECONDS)) && key.isWritable()) {
					msg = (ConnectionMsg) key.attachment();
					try {
						channel = (SocketChannel) key.channel();
						// if (!msg.getAndSetAction(2, 1))
						// continue;
						channel.write(ByteBufferUtil.getBuffer(("服务回复：" + a.addAndGet(1)).getBytes()));
						key = channel.register(key.selector(), SelectionKey.OP_READ, msg.updateConnectionTime().enableSelectable());
						// key.
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (ClosedChannelException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						doFinish(WriteDo, key);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
