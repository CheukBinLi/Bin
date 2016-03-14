package com.cheuks.bin.anythingtest.netty.linebaseframe;

import java.util.Date;

import com.cheuks.bin.anythingtest.netty.BaseByte;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class LineBase_Server {

	static class ChildHandler extends ChannelInboundHandlerAdapter {

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			//			byte[] b = "200".getBytes();
			//			ByteBuf bb = Unpooled.buffer(b.length);
			//			bb.writeBytes(b);
			//			ctx.writeAndFlush(BaseByte.getByteBuf("200".getBytes()));
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			ctx.writeAndFlush(BaseByte.getByteBuf((msg + ":" + new Date()).getBytes()));
			ctx.flush();
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			ctx.close();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		ServerBootstrap sb = new ServerBootstrap();
		sb.group(boss, worker).childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipe = ch.pipeline();
//								pipe.addLast(new LineBasedFrameDecoder(1024));
				pipe.addLast(new StringDecoder());
				pipe.addLast(new ChildHandler());
			}
		});
		sb.channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
		System.out.println("开始侦听");
		ChannelFuture f = sb.bind(10911).sync();
		f.channel().closeFuture().sync();
		System.out.println("服务结束");
	}

}
