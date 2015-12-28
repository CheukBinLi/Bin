package com.cheuks.bin.net.server.event;

import java.io.ByteArrayOutputStream;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import com.cheuks.bin.net.server.handler.MessageInfo;
import com.cheuks.bin.net.server.niothread.Attachment;
import com.cheuks.bin.net.util.ByteBufferUtil;
import com.cheuks.bin.net.util.DefaultSerializImpl;
import com.cheuks.bin.net.util.Serializ;

public class RmiReadEvent implements ReadEvent {

	private Attachment attachment;
	private SocketChannel channel;
	protected static Serializ serializ = new DefaultSerializImpl();

	public SelectionKey process(SelectionKey key) throws Throwable {
		attachment = (Attachment) key.attachment();
		channel = (SocketChannel) key.channel();
		ByteArrayOutputStream out = ByteBufferUtil.getByte(channel);
		if (null != out && out.size() > 0)
			attachment.setAttachment((MessageInfo) serializ.toObject(out));
		else
			attachment.setAttachment(null);
		return key;
	}
}
