package com.cheuks.bin.anythingtest.netty;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public abstract class BaseClient {

	public abstract Bootstrap setting(final Bootstrap client);

	public abstract void connectionFinish(final Channel channel);

	public void connection(InetSocketAddress address) throws InterruptedException {
		EventLoopGroup work = new NioEventLoopGroup();
		Bootstrap client = new Bootstrap();
		client.group(work).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true);
		client = setting(client);
		//		System.out.println("开始连接");
		Channel channel = client.connect(address).sync().channel();
		connectionFinish(channel);
		channel.closeFuture().sync();
		System.out.println("连接结束");
	}

}
