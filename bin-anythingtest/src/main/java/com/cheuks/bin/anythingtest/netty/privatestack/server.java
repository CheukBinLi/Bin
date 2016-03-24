
package com.cheuks.bin.anythingtest.netty.privatestack;

import com.cheuks.bin.anythingtest.netty.BaseServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class server extends BaseServer {

	@Override
	public ServerBootstrap setting(ServerBootstrap server) {
		return server.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new MessageDecoder(1024, 4, 4));
				ch.pipeline().addLast(new MessageEncoder());
				ch.pipeline().addLast(new ServerHandler());
			}
		});
	}

	public static void main(String[] args) throws InterruptedException {
		new server().bind(1191);
	}
}
