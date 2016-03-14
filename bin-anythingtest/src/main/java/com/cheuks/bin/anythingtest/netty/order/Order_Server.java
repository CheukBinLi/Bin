package com.cheuks.bin.anythingtest.netty.order;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class Order_Server {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("启动");
		new Order_Server().bind(9930);
		System.out.println("结束");
	}

	public void bind(int port) throws InterruptedException {

		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		ServerBootstrap server = new ServerBootstrap();
		server.group(boss, worker).channel(NioServerSocketChannel.class);
		server.handler(new LoggingHandler(LogLevel.INFO)).option(ChannelOption.SO_BACKLOG, 128);
		server.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline cp = ch.pipeline();
				//				cp.addLast(new ObjectDecoder(1024 * 1024, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
				cp.addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
				cp.addLast(new ObjectEncoder());
				cp.addLast(new OrderServerHandler());
			}

		});

		server.bind(port).sync().channel().closeFuture().sync();
	}

	static class OrderServerHandler extends ChannelInboundHandlerAdapter {

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			super.channelActive(ctx);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			Xmodel x = (Xmodel) msg;
			System.out.println(x.code + ":" + x.name);
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			for (int i = 0; i < 10; i++) {
				ctx.write(new Xmodel("CD" + i, "小王" + i, null));
			}
			ctx.flush();
			ctx.close();
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			ctx.close();
		}

	}
}
