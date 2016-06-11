package com.cheuks.bin.anythingtest.netty.packagemessage;

import com.cheuks.bin.anythingtest.netty.packagemessage.HandlerCenter.people;
import com.cheuks.bin.anythingtest.netty.packagemessage.MsgBuf.MsgBody;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class ServerHandler extends SimpleChannelInboundHandler<MessagePackage<MsgBody>> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MessagePackage<MsgBody> msg) throws Exception {
		HandlerCenter.join(new people<ChannelHandlerContext>(msg.getMessageBody(), ctx));
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		//		super.userEventTriggered(ctx, evt);
		//		System.err.println("超时");
		//		ctx.channel().disconnect();
		//		ctx.close();
		if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE) {
				System.out.println("read idle");
				ctx.close();
			} else if (event.state() == IdleState.WRITER_IDLE) {
				System.out.println("write idle");
				ctx.close();
			} else if (event.state() == IdleState.ALL_IDLE) {
				System.out.println("all idle");
				ctx.writeAndFlush(new MessagePackage<MsgBuf.MsgBody>(ServiceType.SERVICE_TYPE_HEARTBEAT, null));
			}
		}
	}

}
