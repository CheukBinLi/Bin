package com.cheuks.bin.net.server.handler.test;

import com.cheuks.bin.net.server.event.WriteEvent;
import com.cheuks.bin.net.server.niothread.Attachment;
import com.cheuks.bin.net.util.ByteBufferUtil;
import com.cheuks.bin.net.util.Serializ;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class LogWriteEvent implements WriteEvent {

	private Attachment attachment;
	private SocketChannel channel;

	public SelectionKey process(SelectionKey key, Serializ serializ) throws Throwable {
		attachment = (Attachment) key.attachment();
		channel = (SocketChannel) key.channel();
		channel.write(ByteBufferUtil.newInstance().createPackageByByteBuffer(attachment.getAttachment().getData()));
		attachment.registerClose(key);
		return key;
	}

}
