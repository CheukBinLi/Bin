package com.cheuks.bin.anythingtest.netty.packagemessage;

import java.io.IOException;
import java.util.List;

import com.cheuks.bin.anythingtest.netty.packagemessage.MsgBuf.MsgBody;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

public class XXXXXXX extends ByteToMessageCodec<MessagePackage<MsgBody>> {

	@Override
	protected void encode(ChannelHandlerContext ctx, MessagePackage<MsgBody> msg, ByteBuf out) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) throws IOException {
		String h = System.getProperty("java.library.path");
		System.out.println(ClassLoader.getSystemClassLoader().getResource("").toString().substring(6));
		System.setProperty("java.library.path", ClassLoader.getSystemClassLoader().getResource("").toString().substring(6) + ";" + h);
		System.out.println(System.getProperty("java.library.path"));
		System.err.println(Bootstrap.class.getClassLoader().getSystemResource("").getPath());
		System.out.println();
		System.out.println(ClassLoader.getSystemClassLoader().getSystemResource("").getPath());
	}

}
