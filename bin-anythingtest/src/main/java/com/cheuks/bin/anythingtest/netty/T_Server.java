package com.cheuks.bin.anythingtest.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class T_Server {

	public void bind(int port) throws InterruptedException {

		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		ServerBootstrap sb = new ServerBootstrap();
		sb.group(boss, worker);
		sb.channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new T_Server_Handler());
			}
		}).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);

		ChannelFuture f = sb.bind(port).sync();
		System.out.println("侦听启动");
		f.channel().closeFuture().sync();
		System.out.println("服务结束");
	}

	public static void main(String[] args) throws InterruptedException {
		new T_Server().bind(11339);
	}

}
