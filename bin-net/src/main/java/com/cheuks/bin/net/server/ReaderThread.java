package com.cheuks.bin.net.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

import com.cheuks.bin.net.util.ByteBufferUtil;

public class ReaderThread extends AbstractControlThread {

	@Override
	public void run() {// System.err.println("接收器启动");
		while (!Thread.interrupted()) {
			try {
				if (null != (key = READER_QUEUE.poll(5, TimeUnit.MICROSECONDS))) {
					attachment = (Attachment) key.attachment();
					try {
						channel = (SocketChannel) key.channel();
						ByteArrayOutputStream out = ByteBufferUtil.getByte(channel);
						System.out.println(new String(out.toByteArray()));
						key = attachment.unLockAndUpdateHeartBeat(channel, key, SelectionKey.OP_WRITE);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (ClosedChannelException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (key != null)
							tryDo(RELEASE, key);
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
