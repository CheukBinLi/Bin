package com.cheuks.bin.anythingtest.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public abstract class BaseServer {

	public abstract ServerBootstrap setting(final ServerBootstrap server);

	public void bind(int port) throws InterruptedException {
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup work = new NioEventLoopGroup();
		ServerBootstrap server = new ServerBootstrap();
		try {
			server.group(boss, work).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 128).handler(new LoggingHandler(LogLevel.INFO));
			server = setting(server);
			System.out.println("服务运行");
			server.bind(port).sync().channel().closeFuture().sync();
			System.out.println("服务结束");
		} finally {
			boss.shutdownGracefully();
			work.shutdownGracefully();
		}
	}

}
