package com.cheuks.bin.anythingtest.netty.privatestack;

import com.cheuks.bin.anythingtest.netty.privatestack.Message.Header;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<Message> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Header h = new Header();
		h.setLength(1).setType(10).setSessionID(10068).setVersion(10010);
		ctx.writeAndFlush(new Message(h, null));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		cause.printStackTrace();
	}

}
