package com.cheuks.bin.anythingtest.netty.packagemessage;

import com.cheuks.bin.anythingtest.netty.packagemessage.MsgBuf.MsgBody;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import java.net.InetSocketAddress;

public class ClientEX_Main {

	public static void main(String[] args) throws InterruptedException {
		//线程池服务
		InetSocketAddress address = new InetSocketAddress("127.0.0.1", 1190);
		MessagePackage<MsgBody> heartbeat = new MessagePackage<MsgBuf.MsgBody>(ServiceType.SERVICE_TYPE_HEARTBEAT, null);
		//直充单例
		ChannelPoolService cps = new ChannelPoolService(address, true, 2000, heartbeat) {
			@Override
			public Bootstrap setting(Bootstrap client) {
				return client.handler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new MessageCodec());
						//						ch.pipeline().addLast(new IdleStateHandler(5, 5, 10));
						ch.pipeline().addLast(new ClientHandler());
					}
				});
			}
		};
		cps.start();

		//		for (int i = 0; i++ < 10;) {
		//			long s1 = System.currentTimeMillis();
		//			//测试
		//			MsgBody.Builder builder = MsgBody.newBuilder();
		//			builder.setUid("cbd").setMethod(Integer.toString("java.lang.String:A".hashCode())).setVersion("1");
		//			MessagePackage<MsgBody> messagePackage = new MessagePackage<MsgBuf.MsgBody>(ServiceType.SERVICE_TYPE_SERVICE, builder.build());
		//			Channel c = ChannelPoolService.getSingleinstance().pop();
		//			c.writeAndFlush(messagePackage);
		//			c.attr(BaseClient.CLIENT).get().await(c);
		//			Object o = ChannelCachePool.newInstance().getObject(c);
		//			long s2 = System.currentTimeMillis();
		//			System.out.println(o);
		//			System.out.println(i + ":" + (s2 - s1));
		//		}
		//		cps.stop();
		//		cps.start();
	}

}
