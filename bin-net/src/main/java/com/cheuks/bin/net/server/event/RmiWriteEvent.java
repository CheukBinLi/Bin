package com.cheuks.bin.net.server.event;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import com.cheuks.bin.net.server.handler.MessageInfo;
import com.cheuks.bin.net.server.niothread.Attachment;
import com.cheuks.bin.net.util.ByteBufferUtil;
import com.cheuks.bin.net.util.DefaultSerializImpl;
import com.cheuks.bin.net.util.Serializ;

public class RmiWriteEvent implements WriteEvent {

	private Attachment attachment;
	private SocketChannel channel;
	protected static Serializ serializ = new DefaultSerializImpl();

	public SelectionKey process(SelectionKey key) throws Throwable {
		attachment = (Attachment) key.attachment();
		channel = (SocketChannel) key.channel();
		// channel.write(ByteBufferUtil.getBuffer(("服务回复：" +
		channel.write(ByteBufferUtil.getBuffer(serializ.serializ((MessageInfo) attachment.getAttachment())));
		//			throw new Throwable("channel读取失败");
		//注册读
		//		attachment.registerRead();
		attachment.registerClose(key);
		return key;
	}
}
