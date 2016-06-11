package com.cheuks.bin.anythingtest.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class T_Client {

	public void connection(String hostname, int port) {

		try {
			EventLoopGroup worker = new NioEventLoopGroup();
			Bootstrap b = new Bootstrap();
			b.group(worker);
			b.channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new T_Client_Handler());
				}
			}).option(ChannelOption.SO_KEEPALIVE, true);

			System.out.println("连接开始");
			ChannelFuture f = b.connect(new InetSocketAddress(hostname, port));
			f.channel().closeFuture().sync();
			System.out.println("连接结束");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		new T_Client().connection("127.0.0.1", 11339);
	}

}
