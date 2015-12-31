package com.cheuks.bin.net.server.event;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import com.cheuks.bin.net.server.handler.MessageInfo;
import com.cheuks.bin.net.server.niothread.Attachment;
import com.cheuks.bin.net.util.ByteBufferUtil;
import com.cheuks.bin.net.util.DefaultSerializImpl;
import com.cheuks.bin.net.util.Serializ;
import com.cheuks.bin.net.util.ByteBufferUtil.DataPacket;

public class RmiWriteEvent implements WriteEvent {

	private Attachment attachment;
	private SocketChannel channel;

	public SelectionKey process(SelectionKey key, Serializ serializ) throws Throwable {
		attachment = (Attachment) key.attachment();
		channel = (SocketChannel) key.channel();
		DataPacket dataPacket = attachment.getAttachmentX();
		channel.write(ByteBufferUtil.newInstance().create);
		// throw new Throwable("channel读取失败");
		// 注册读
		if (mi.isShortConnect())
			attachment.registerClose(key);
		else
			attachment.registerRead();
		return key;
	}
}
