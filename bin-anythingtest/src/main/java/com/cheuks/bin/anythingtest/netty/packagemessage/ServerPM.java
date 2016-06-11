package com.cheuks.bin.anythingtest.netty.packagemessage;

import com.cheuks.bin.anythingtest.netty.BaseServer;
import com.cheuks.bin.anythingtest.netty.packagemessage.i.X1;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class ServerPM extends BaseServer {

	@Override
	public ServerBootstrap setting(ServerBootstrap server) {
		return server.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast(new IdleStateHandler(60, 55, 50));
				pipeline.addLast(new MessageCodec());
				pipeline.addLast(new ServerHandler());
			}
		});
	}

	public static void main(String[] args) throws InterruptedException, InstantiationException, IllegalAccessException {
		ClassInfo<?> ci = new ClassInfo(X1.class);
		DefaultCachePool.newInstance().putObject(ci.getUid(), ci);
		HandlerCenter.newInstance().start();
		new ServerPM().bind(1190);
	}

}
