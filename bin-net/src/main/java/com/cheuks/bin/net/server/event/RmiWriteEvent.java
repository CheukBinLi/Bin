package com.cheuks.bin.net.server.event;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import com.cheuks.bin.net.server.handler.MessageInfo;
import com.cheuks.bin.net.server.niothread.Attachment;
import com.cheuks.bin.net.util.ByteBufferUtil;
import com.cheuks.bin.net.util.Serializ;

public class RmiWriteEvent implements WriteEvent {

	private Attachment attachment;
	private SocketChannel channel;

	public SelectionKey process(SelectionKey key, Serializ serializ) throws Throwable {
		attachment = (Attachment) key.attachment();
		channel = (SocketChannel) key.channel();
		MessageInfo mi = (MessageInfo) attachment.getAttachment();
		channel.write(ByteBufferUtil.getBuffer(serializ.serializ(mi)));
		if (mi.isShortConnect())
			attachment.registerClose(key);
		else
			attachment.registerRead();
		return key;
	}
}
