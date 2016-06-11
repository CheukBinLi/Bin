package com.cheuks.bin.anythingtest.netty.protobuf;

import com.cheuks.bin.anythingtest.netty.protobuf.Xprotobuf.XprotobufModel;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<XprotobufModel> {

	private volatile Channel channel;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, XprotobufModel msg) throws Exception {
		System.out.println("Client:xxxxxxxxxxxx");
	}

	@Override
	public boolean acceptInboundMessage(Object msg) throws Exception {
		return super.acceptInboundMessage(msg);
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
		channel = ctx.channel();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		XprotobufModel.Builder builder = XprotobufModel.newBuilder();
		XprotobufModel x = builder.setID(1).setUrl("abcde").build();
		ctx.writeAndFlush(Unpooled.copiedBuffer(x.toByteArray()));
	}

}
