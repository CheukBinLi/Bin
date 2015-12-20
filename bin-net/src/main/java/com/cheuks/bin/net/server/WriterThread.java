package com.cheuks.bin.net.server;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.cheuks.bin.net.util.ByteBufferUtil;

public class WriterThread extends AbstractControlThread {

	private static final AtomicInteger a = new AtomicInteger();

	@Override
	public void run() {

		while (!Thread.interrupted()) {
			try {
				if (null != (key = WRITER_QUEUE.poll(5, TimeUnit.MICROSECONDS))) {
					attachment = (Attachment) key.attachment();
					try {
						channel = (SocketChannel) key.channel();
						channel.write(ByteBufferUtil.getBuffer(("服务回复：" + a.addAndGet(1)).getBytes()));
						key = attachment.unLockAndUpdateHeartBeat(channel, key, SelectionKey.OP_READ);
						// key.
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (ClosedChannelException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
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
