package com.cheuks.bin.net.server.event;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import com.cheuks.bin.net.server.niothread.Attachment;
import com.cheuks.bin.net.util.ByteBufferUtil;
import com.cheuks.bin.net.util.Serializ;

public class MessageWriteEvent implements WriteEvent {

	private Attachment attachment;
	private SocketChannel channel;

	public SelectionKey process(SelectionKey key, Serializ serializ) throws Throwable {
		attachment = (Attachment) key.attachment();
		channel = (SocketChannel) key.channel();
		// channel.write(ByteBufferUtil.getBuffer(("服务回复：" +
		//#		channel.write(ByteBufferUtil.getBuffer((byte[]) attachment.getAttachmentX()));
		// throw new Throwable("channel读取失败");
		// 注册读
		//		if (mi.isShortConnect())
		attachment.registerClose(key);
		//		else
		//			attachment.registerRead();
		return key;
	}
}
