package com.cheuks.bin.anythingtest.zookeeper.paxos.net.mananger;

import java.io.ByteArrayOutputStream;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

import com.cheuks.bin.anythingtest.zookeeper.paxos.net.ConnectionMsg;
import com.cheuks.bin.net.util.ByteBufferUtil2;

public class ReaderMananger extends AbstractMananger {

	@Override
	public void run() {
		// System.err.println("接收器启动");
		while (!Thread.interrupted()) {
			try {
				if (null != (key = ReaderQueue.poll(5, TimeUnit.MICROSECONDS)) && key.isValid() && key.isReadable()) {
					msg = (ConnectionMsg) key.attachment();
					try {
						channel = (SocketChannel) key.channel();
						ByteArrayOutputStream out = ByteBufferUtil2.getByte(channel);
						// channel.shutdownInput();
						key = channel.register(key.selector(), SelectionKey.OP_WRITE, msg.enableSelectable().updateConnectionTime());
						key.selector().wakeup();
						// key.
					} catch (NumberFormatException e) {
						e.printStackTrace();
						System.out.println("no:" + msg.getNo() + "  - " + msg.getAction() + " - uuid: " + msg.getId());
					} catch (ClosedChannelException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						doFinish(ReadDo, key);
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
