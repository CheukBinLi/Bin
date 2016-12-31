package com.cheuks.bin.anythingtest.netty;

import com.cheuks.bin.anythingtest.netty.packagemessage.CachePoolAdapter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.net.InetSocketAddress;

@SuppressWarnings("unchecked")
public abstract class BaseClient {

	public abstract Bootstrap setting(final Bootstrap client);

	public static final AttributeKey<BaseClient> CLIENT = AttributeKey.newInstance("CLIENT");

	private EventLoopGroup work = new NioEventLoopGroup();

	private Bootstrap client = new Bootstrap().group(work).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true);

	public Channel connection(InetSocketAddress address) throws InterruptedException {
		client = setting(client).attr(CLIENT, this);
		return (Channel) client.connect(address).sync().channel().closeFuture().addListener(new GenericFutureListener<Future<? super Void>>() {

			public void operationComplete(Future<? super Void> future) throws Exception {
				System.out.println(future.cause());
			}
		}).channel();
	}

	public void await(Channel channel) {
		try {
			synchronized (channel) {
				channel.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void walkup(Channel channel) {
		synchronized (channel) {
			channel.notify();
		}
	}

	public <T> T receiveCallback(Channel channel) {
		await(channel);
		Object result = CachePoolAdapter.newInstance().getObject(channel);
		return null == result ? null : (T) result;
	}
}
