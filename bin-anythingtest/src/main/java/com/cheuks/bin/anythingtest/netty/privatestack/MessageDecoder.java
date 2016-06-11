package com.cheuks.bin.anythingtest.netty.privatestack;

import com.cheuks.bin.anythingtest.netty.privatestack.Message.Header;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.util.Map;

public class MessageDecoder extends LengthFieldBasedFrameDecoder {

	public MessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		//		buf.writeInt(msg.getHeader().getType());
		if (in.readInt() != Message.firstByte)
			return null;
		int type = in.readInt();
		if (type == -1)
			return null;
		Message.Header header = new Header();
		//		buf.writeLong(msg.getHeader().getLength());
		header.setLength(in.readLong()).setSessionID(in.readLong());
		//		buf.writeLong(msg.getHeader().getSessionID());
		//		byte[] attachment = serial.objectToBytes(msg.getHeader().getAttchment());

		//		buf.writeLong(attachment.length);
		//		int way = buf.arrayOffset();
		long attachLength = in.readLong();
		byte[] attach;
		if (attachLength > 0) {
			attach = new byte[(int) attachLength];
			//		buf.writeBytes(attachment);
			//		byte[] body = serial.objectToBytes(msg.getBody());
			in.readBytes(attach);
			header.setAttchment((Map<String, Object>) serial.byteToOject(attach));
		}
		long bodyLength = in.readLong();
		byte[] body = null;
		if (bodyLength > 0) {
			body = new byte[(int) bodyLength];
			in.readBytes(body);
		}
		//		buf.writeLong(1);
		//		buf.setLong(way, body.length);
		//		buf.writeBytes(body);
		Message message = new Message(header, serial.byteToOject(body));
		return message;
	}

}
