package com.cheuks.bin.net.client;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

import com.cheuks.bin.net.server.handler.MessageInfo;
import com.cheuks.bin.net.server.handler.test.ServiceHandlerTestI;
import com.cheuks.bin.net.util.ByteBufferUtil;
import com.cheuks.bin.net.util.DefaultSerializImpl;
import com.cheuks.bin.net.util.Serializ;
import com.cheuks.bin.util.ReflectionUtil;

public class client {

	private SocketChannel channel;

	void init() {
		try {
			channel = SocketChannel.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws NumberFormatException, Throwable {

		Serializ s = new DefaultSerializImpl();
		ServiceHandlerTestI a = null;
		Method m = ServiceHandlerTestI.class.getDeclaredMethod("a");
		client c = new client();
		c.init();

		MessageInfo messageInfo = new MessageInfo();
		messageInfo.setPath("x/1.0").setMethod(ReflectionUtil.newInstance().getMethodName(m));

		System.err.println(messageInfo);
		System.err.println(messageInfo.getMethod());

		c.channel.connect(new InetSocketAddress("127.0.0.1", 10086));
		c.channel.write(ByteBufferUtil.getBuffer(s.serializ(messageInfo)));
	}

}
