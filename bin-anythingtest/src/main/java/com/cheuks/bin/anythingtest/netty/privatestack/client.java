package com.cheuks.bin.anythingtest.netty.privatestack;

import com.cheuks.bin.anythingtest.netty.BaseClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import java.net.InetSocketAddress;

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
		new client().connection(new InetSocketAddress("127.0.0.1", 1191));
	}

}
