package com.cheuks.bin.anythingtest.netty.privatestack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<Message> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf buf) throws Exception {

		buf.writeInt(msg.firstByte);
		buf.writeInt(msg.getHeader().getType());
		buf.writeLong(msg.getHeader().getLength());
		buf.writeLong(msg.getHeader().getSessionID());
		byte[] attachment = serial.objectToBytes(msg.getHeader().getAttchment());
		buf.writeLong(attachment.length);
		buf.writeBytes(attachment);
		byte[] body = serial.objectToBytes(msg.getBody());
		//int way = buf.arrayOffset();
		buf.writeLong(body.length);
		buf.writeBytes(body);
		//		buf.setLong(way, body.length);

	}

}
