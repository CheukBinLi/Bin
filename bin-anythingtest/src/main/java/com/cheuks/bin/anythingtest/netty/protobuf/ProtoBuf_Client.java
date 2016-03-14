package com.cheuks.bin.anythingtest.netty.protobuf;

import java.net.InetSocketAddress;

import com.cheuks.bin.anythingtest.netty.BaseClient;
import com.cheuks.bin.anythingtest.netty.protobuf.Xprotobuf.XprotobufModel;
import com.cheuks.bin.anythingtest.netty.protobuf.Xprotobuf.XprotobufModel.Builder;
import com.cheuks.bin.anythingtest.netty.protobuf.ex.WorldClockProtocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class ProtoBuf_Client extends BaseClient {

	public static void main(String[] args) {
		try {
			new ProtoBuf_Client().connection(new InetSocketAddress("127.0.0.1", 10010));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Bootstrap setting(Bootstrap client) {
		return client.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline p = ch.pipeline();
				p.addLast(new ProtobufVarint32FrameDecoder());
				p.addLast(new ProtobufDecoder(Xprotobuf.XprotobufModel.getDefaultInstance()));
				p.addLast(new ProtobufVarint32LengthFieldPrepender());
				p.addLast(new ProtobufEncoder());
				p.addLast("handler", new handler());
			}
		});
	}

	static class handler extends SimpleChannelInboundHandler<XprotobufModel> {

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			System.out.println("a");
			Xprotobuf.XprotobufModel.Builder builder = XprotobufModel.newBuilder();
			builder.setID(1111);
			ctx.write(builder.build());
			ctx.flush();
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			ctx.close();
		}

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, XprotobufModel msg) throws Exception {
			System.out.println("b");
			System.out.println(msg.getID() + ":" + msg.getUrl());

		}

	}

}
