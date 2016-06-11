package com.cheuks.bin.anythingtest.netty.protobuf;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class Server {

	public void bind(int port) throws InterruptedException {
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(boss, worker);
		serverBootstrap.channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 100);
		serverBootstrap.localAddress(port).handler(new LoggingHandler(LogLevel.INFO));
		serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipe = ch.pipeline();
				pipe.addLast(new ProtobufVarint32FrameDecoder());
				pipe.addLast(new ProtobufDecoder(Xprotobuf.XprotobufModel.getDefaultInstance()));
				pipe.addLast(new ProtobufVarint32LengthFieldPrepender());
				pipe.addLast(new ProtobufEncoder());
				pipe.addLast(new ServerHandler());
			}
		});
		serverBootstrap.bind().addListener(new GenericFutureListener<Future<? super Void>>() {

			public void operationComplete(Future<? super Void> future) throws Exception {
				if (future.cause() != null) {
					future.cause().printStackTrace();
					return;
				}
				System.err.println("启动成功");
			}
		}).channel().closeFuture().sync();
		System.err.println("结束");
	}

	public static void main(String[] args) throws InterruptedException {
		new Server().bind(1919);
	}

}
