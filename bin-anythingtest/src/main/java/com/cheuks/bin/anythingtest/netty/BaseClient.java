package com.cheuks.bin.anythingtest.netty;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

public abstract class BaseClient {

	public abstract Bootstrap setting(final Bootstrap client);

	public static final AttributeKey<BaseClient> CLIENT = AttributeKey.newInstance("CLIENT");

	private Object receive;

	private Channel channel;

	public void connection(InetSocketAddress address) throws InterruptedException {
		EventLoopGroup work = new NioEventLoopGroup();
		Bootstrap client = new Bootstrap();
		client.group(work).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true);
		client = setting(client);
		//		System.out.println("开始连接");
		Channel channel = client.connect(address).sync().channel();
		channel.closeFuture().sync();
		System.out.println("连接结束");
	}

	public void await() {
		try {
			synchronized (channel) {
				channel.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void walkup() {
		synchronized (channel) {
			channel.notify();
		}
	}

	public <T> T receiveCallback() {
		await();
		return null == getReceive() ? null : (T) getReceive();
	}

	public Object getReceive() {
		return receive;
	}

	public BaseClient setReceive(Object receive) {
		this.receive = receive;
		return this;
	}

	public Channel getChannel() {
		return channel;
	}

	public BaseClient setChannel(Channel channel) {
		this.channel = channel;
		return this;
	}

}
