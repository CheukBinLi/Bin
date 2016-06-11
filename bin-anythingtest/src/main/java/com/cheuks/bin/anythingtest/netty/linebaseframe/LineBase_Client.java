package com.cheuks.bin.anythingtest.netty.linebaseframe;

import com.cheuks.bin.anythingtest.netty.BaseByte;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import java.net.InetSocketAddress;

public class LineBase_Client {

	static class ChildHandler extends ChannelInboundHandlerAdapter {

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			System.out.println("a");
			for (int i = 0; i < 10; i++)
				ctx.writeAndFlush(BaseByte.getByteBuf("当前时间".getBytes()));
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			// TODO Auto-generated method stub
			System.out.println("b");
			System.out.println(msg);
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			// TODO Auto-generated method stub
			System.out.println("c");
			super.exceptionCaught(ctx, cause);
		}

	}

	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup worker = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(worker).handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipe = ch.pipeline();
//								pipe.addLast(new LineBasedFrameDecoder(1024));
				pipe.addLast(new StringDecoder());
				pipe.addLast(new ChildHandler());
			}
		});
		b.channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true);
		ChannelFuture f = b.connect(new InetSocketAddress("127.0.0.1", 10911)).sync();
		System.out.println("开始连接");
		f.channel().closeFuture().sync();
		System.out.println("连接结束");
	}

}
