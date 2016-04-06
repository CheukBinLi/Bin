package com.cheuks.bin.anythingtest.netty.packagemessage;

import java.net.InetSocketAddress;

import com.cheuks.bin.anythingtest.netty.BaseClient;
import com.cheuks.bin.anythingtest.netty.packagemessage.MsgBuf.MsgBody;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class client extends BaseClient {

	@Override
	public Bootstrap setting(Bootstrap client) {
		return client.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new MessageCodec());
				ch.pipeline().addLast(new ClientHandler());
			}
		});
	}

	public static void main(String[] args) throws InterruptedException {
		new client().connection(new InetSocketAddress("192.168.168.200", 1190));
		//		MsgBody.Builder builder = MsgBody.newBuilder();
		//		builder.setUid("cbd").setMethod(Integer.toString("java.lang.String:A".hashCode())).setVersion("1");
		//		MessagePackage<MsgBody> messagePackage = new MessagePackage<MsgBuf.MsgBody>(ServiceType.SERVICE_TYPE_SERVICE, builder.build());
		//		channel.writeAndFlush(messagePackage).sync();
	}

}
