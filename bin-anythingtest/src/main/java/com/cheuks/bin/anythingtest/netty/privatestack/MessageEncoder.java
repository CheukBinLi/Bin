package com.cheuks.bin.anythingtest.netty.privatestack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<Message> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf buf) throws Exception {
		int a = 0;
		a += 4;
		buf.writeInt(msg.firstByte);
		a += 4;
		buf.writeInt(msg.getHeader().getType());
		a += 4;
		buf.writeLong(msg.getHeader().getLength());
		a += 8;
		buf.writeLong(msg.getHeader().getSessionID());
		a += 8;
		byte[] attachment = serial.objectToBytes(msg.getHeader().getAttchment());
		buf.writeLong(attachment.length);
		a += 8;
		buf.writeBytes(attachment);
		a += attachment.length;
		byte[] body = serial.objectToBytes(msg.getBody());
		// int way = buf.arrayOffset();
		buf.writeLong(body.length);
		a += 8;
		buf.writeBytes(body);
		a += body.length;
		// buf.setLong(way, body.length);
		System.err.println(a);

	}

}
