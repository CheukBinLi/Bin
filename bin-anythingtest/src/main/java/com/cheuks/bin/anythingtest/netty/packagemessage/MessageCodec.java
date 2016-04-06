package com.cheuks.bin.anythingtest.netty.packagemessage;

import java.util.List;

import com.cheuks.bin.anythingtest.netty.packagemessage.MsgBuf.MsgBody;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

public class MessageCodec extends ByteToMessageCodec<MessagePackage<MsgBody>> {

	@Override
	protected void encode(ChannelHandlerContext ctx, MessagePackage<MsgBody> msg, ByteBuf out) throws Exception {
		// TODO Auto-generated method stub
		byte[] body = msg.getMessageBody().toByteArray();
		out.writeInt(msg.getHead());
		out.writeInt(msg.getServiceType().getValue());
		out.writeInt(body.length).writeBytes(body);
		out.writeInt(msg.getEnd());
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		Object decoded = decode(ctx, in);
		if (decoded != null) {
			out.add(decoded);
		}
	}

	protected MessagePackage<MsgBody> decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		//16
		//		byte[] head = new byte[4];
		//		for (int i = 0; i < head.length && in.isReadable(); i++) {
		//			head[i] = in.readByte();
		//		}
		//		in.isReadable();
		//		in.resetReaderIndex();
		MessagePackage<MsgBody> messagePackage = null;
		if (in.readInt() == MessagePackage.head) {
			if (ServiceType.SERVICE_TYPE_HEARTBEAT.getValue() == in.readInt())
				return messagePackage;
			byte[] data = new byte[in.readInt()];
			in.readBytes(data);
			messagePackage = new MessagePackage<MsgBuf.MsgBody>(MsgBody.parseFrom(data));
			in.readInt();
		}
		return messagePackage;
	}

}
