package com.cheuks.bin.anythingtest.netty.packagemessage;

import com.cheuks.bin.anythingtest.netty.BaseClient;
import com.cheuks.bin.anythingtest.netty.packagemessage.MsgBuf.MsgBody;

import io.netty.channel.ChannelHandlerContext;

public class ClientHandler extends SimpleChannelInboundHandlerEX<MessagePackage<MsgBody>> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MessagePackage<MsgBody> msg) throws Exception {
		Object o = ctx.channel().attr(BaseClient.CLIENT);
		CachePoolAdapter.newInstance().putObject(ctx.channel(), msg);
		ctx.channel().attr(BaseClient.CLIENT).get().walkup(ctx.channel());
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		cause.printStackTrace();
	}
}
