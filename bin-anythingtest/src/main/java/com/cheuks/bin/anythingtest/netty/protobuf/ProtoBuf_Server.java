package com.cheuks.bin.anythingtest.netty.protobuf;

import com.cheuks.bin.anythingtest.netty.BaseServer;
import com.cheuks.bin.anythingtest.netty.protobuf.ProtoBuf_Client.handler;
import com.cheuks.bin.anythingtest.netty.protobuf.Xprotobuf.XprotobufModel;

import io.netty.bootstrap.ServerBootstrap;
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

public class ProtoBuf_Server extends BaseServer {

	public static void main(String[] args) {
		try {
			new ProtoBuf_Server().bind(10010);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ServerBootstrap setting(ServerBootstrap server) {
		return server.childHandler(new ChannelInitializer<SocketChannel>() {

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
		}

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, XprotobufModel msg) throws Exception {
			System.out.println("b");
			for (int i = 0; i < 10; i++)
				//			Xprotobuf.XprotobufModel.Builder build = XprotobufModel.newBuilder();
				ctx.write(XprotobufModel.newBuilder().setID(i).setUrl("url:" + i).build());
			ctx.flush();
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		}

	}

}
