package com.cheuks.bin.anythingtest.netty.protobuf;

import com.cheuks.bin.anythingtest.netty.protobuf.Xprotobuf.XprotobufModel;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class Client {

	public void connection(String address, int port) throws InterruptedException {

		AttributeKey<String> a = AttributeKey.valueOf("aa");

		EventLoopGroup worker = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(worker);
		bootstrap.remoteAddress(address, port);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipe = ch.pipeline();
				pipe.addLast(new ProtobufVarint32FrameDecoder());
				pipe.addLast(new ProtobufDecoder(XprotobufModel.getDefaultInstance()));
				pipe.addLast(new ProtobufVarint32LengthFieldPrepender());
				pipe.addLast(new ProtobufEncoder());
				pipe.addLast(new ClientHandler());
			}
		});
		bootstrap.connect().addListener(new GenericFutureListener<Future<? super Void>>() {

			public void operationComplete(Future<? super Void> future) throws Exception {
				if (future.cause() != null) {
					future.cause().printStackTrace();
					return;
				}
				System.err.println("连接成功");
			}
		}).channel().closeFuture().sync();
		System.err.println("结束");
	}

	public static void main(String[] args) throws InterruptedException {
		new Client().connection("127.0.0.1", 1919);
	}

}
