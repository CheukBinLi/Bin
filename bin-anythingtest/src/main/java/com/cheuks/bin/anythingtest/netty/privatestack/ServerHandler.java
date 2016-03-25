package com.cheuks.bin.anythingtest.netty.privatestack;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

public class ServerHandler extends SimpleChannelInboundHandler<Message> {

	Channel channel;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
//		System.err.println(msg.getHeader().getSessionID());
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		this.channel = ctx.channel();
		super.channelRegistered(ctx);
		System.out.println(ctx.channel().attr(server.id).get().addAndGet(1));
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		super.userEventTriggered(ctx, evt);
		System.err.println("超时");
		ctx.channel().disconnect();
		ctx.close();
	}

}
