package com.cheuks.bin.anythingtest.netty.packagemessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public abstract class SimpleChannelInboundHandlerEX<I> extends SimpleChannelInboundHandler<I> {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		super.channelRead(ctx, msg);
		ChannelPoolService.getSingleinstance().release(ctx.channel());
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, I msg) throws Exception {
	}

}
