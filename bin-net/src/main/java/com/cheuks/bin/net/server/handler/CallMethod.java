package com.cheuks.bin.net.server.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

import com.cheuks.bin.net.util.ByteBufferUtil;
import com.cheuks.bin.net.util.DefaultSerializImpl;
import com.cheuks.bin.net.util.Serializ;

public abstract class CallMethod {

	protected SocketChannel channel;
	protected static final Serializ defaultSerializ = new DefaultSerializImpl();

	public Socket getConnection(String path) throws IOException {
		// channel = SocketChannel.open();
		// channel.configureBlocking(false);
		// channel.connect(new InetSocketAddress("127.0.0.1", 10088));
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress("127.0.0.1", 10088), 60000);
		return socket;
	}

	public Object call(String path, String methodName, Object... params) throws NumberFormatException, Throwable {
		MessageInfo messageInfo = new MessageInfo();
		messageInfo.setPath("x/1.0").setMethod("java.lang.String:a");
		Socket s = getConnection(null);
		OutputStream out = s.getOutputStream();
		InputStream in = s.getInputStream();
		out.write(ByteBufferUtil.getBuffer(defaultSerializ.serializ(messageInfo)).array());
		out.flush();
		messageInfo = defaultSerializ.toObject(ByteBufferUtil.getByte(in));
		return messageInfo.getResult();
	}

}
