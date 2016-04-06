package com.cheuks.bin.anythingtest.netty.privatestack;

import java.net.InetSocketAddress;

import com.cheuks.bin.anythingtest.netty.BaseClient;
import com.cheuks.bin.anythingtest.netty.BaseServer;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class client extends BaseClient {

	@Override
	public Bootstrap setting(Bootstrap client) {
		return client.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new MessageDecoder(1024, 4, 4));
				ch.pipeline().addLast(new MessageEncoder());
				ch.pipeline().addLast(new ClientHandler());
			}
		});
	}

	public static void main(String[] args) throws InterruptedException {
		new client().connection(new InetSocketAddress("192.168.168.43", 1191));
	}

	@Override
	public void connectionFinish(Channel channel) {
		// TODO Auto-generated method stub

	}
}
