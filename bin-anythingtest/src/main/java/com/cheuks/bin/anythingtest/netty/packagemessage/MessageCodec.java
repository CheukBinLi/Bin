package com.cheuks.bin.anythingtest.netty.packagemessage;

import com.cheuks.bin.anythingtest.netty.packagemessage.MsgBuf.MsgBody;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

public class MessageCodec extends ByteToMessageCodec<MessagePackage<MsgBody>> {

	@Override
	protected void encode(ChannelHandlerContext ctx, MessagePackage<MsgBody> msg, ByteBuf out) throws Exception {
		// TODO Auto-generated method stub
		out.writeInt(msg.getHead());
		out.writeInt(msg.getServiceType().getValue());
		if (null != msg.getMessageBody()) {
			byte[] body = msg.getMessageBody().toByteArray();
			out.writeInt(body.length).writeBytes(body);
		} else
			out.writeInt(0);
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
			if (ServiceType.SERVICE_TYPE_HEARTBEAT.getValue() == in.readInt()) {
				System.err.println("心跳");
				ctx.writeAndFlush(new MessagePackage<MsgBuf.MsgBody>(ServiceType.SERVICE_TYPE_HEARTBEAT, null));
				return messagePackage;
			}
			byte[] data = new byte[in.readInt()];
			in.readBytes(data);
			messagePackage = new MessagePackage<MsgBuf.MsgBody>(MsgBody.parseFrom(data));
			in.readInt();
		}
		return messagePackage;
	}

}
