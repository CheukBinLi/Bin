package com.cheuks.bin.net.server.event;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import com.cheuks.bin.net.server.niothread.Attachment;
import com.cheuks.bin.net.util.ByteBufferUtil;
import com.cheuks.bin.net.util.ByteBufferUtil.DataPacket;
import com.cheuks.bin.net.util.DefaultSerializImpl;
import com.cheuks.bin.net.util.Serializ;

public class MessageReadEvent implements ReadEvent {

	private Attachment attachment;
	private SocketChannel channel;
	protected static Serializ serializ = new DefaultSerializImpl();

	public SelectionKey process(SelectionKey key) throws Throwable {
		attachment = (Attachment) key.attachment();
		channel = (SocketChannel) key.channel();

		DataPacket dataPacket = ByteBufferUtil.newInstance().getData(channel);

		if (null != out && out.size() > 0)
			attachment.setAttachment(out);
		else
			attachment.setAttachment(null);
		return key;
	}
}
