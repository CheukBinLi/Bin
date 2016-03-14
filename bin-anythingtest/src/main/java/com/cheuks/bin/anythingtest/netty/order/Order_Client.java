package com.cheuks.bin.anythingtest.netty.order;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class Order_Client {

	public static void main(String[] args) throws InterruptedException {
		new Order_Client().conn(new InetSocketAddress("127.0.0.1", 9930));
	}

	public void conn(InetSocketAddress address) throws InterruptedException {
		EventLoopGroup work = new NioEventLoopGroup();
		Bootstrap client = new Bootstrap();
		client.group(work).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true);
		client.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline cp = ch.pipeline();
				//				cp.addLast(new ObjectDecoder(1024 * 1024, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
				cp.addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
				cp.addLast(new ObjectEncoder());
				cp.addLast(new OrderClientHandler());
			}

		});
		client.connect(address).sync().channel().closeFuture().sync();
	}

	static class OrderClientHandler extends ChannelInboundHandlerAdapter {

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			Xmodel x = new Xmodel("CD123", "小王", null);
			ctx.writeAndFlush(x);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			Xmodel x = (Xmodel) msg;
			System.out.println(x.code + ":" + x.name);
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			ctx.close();
		}

	}

}
