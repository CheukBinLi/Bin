package com.cheuks.bin.anythingtest.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public abstract class BaseServer {

	public abstract ServerBootstrap setting(final ServerBootstrap server);

	public void bind(int port) throws InterruptedException {
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup work = new NioEventLoopGroup();
		ServerBootstrap server = new ServerBootstrap();
		try {
			server.group(boss, work).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 128).handler(new LoggingHandler(LogLevel.INFO));
			server = setting(server);

			server.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {

				public void operationComplete(Future<? super Void> future) throws Exception {
					if (null == future.cause())
						System.out.println("服务运行");
					else
						future.cause().printStackTrace();
				}
			}).channel().closeFuture().sync();
			System.out.println("服务结束");
		} finally {
			boss.shutdownGracefully();
			work.shutdownGracefully();
		}
	}

}
