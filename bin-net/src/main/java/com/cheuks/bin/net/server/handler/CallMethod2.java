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

public class CallMethod2 {

	protected SocketChannel channel;
	protected static Serializ defaultSerializ = new DefaultSerializImpl();
	protected Socket s;
	private String ip;
	private int port;

	public CallMethod2(String ip, int port) {
		super();
		this.ip = ip;
		this.port = port;
	}

	public Socket getConnection() throws IOException {
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress(this.ip, this.port));
		return socket;
	}

	public Object call(String path, String methodName, Object... params) throws NumberFormatException, Throwable {
		MessageInfo messageInfo = new MessageInfo();
		messageInfo.setPath(path).setMethod(methodName);
		messageInfo.setParams(params);
		s = getConnection();
		OutputStream out = s.getOutputStream();
		out.write(ByteBufferUtil.getBuffer(defaultSerializ.serializ(messageInfo)).array());
		InputStream in = s.getInputStream();
		messageInfo = defaultSerializ.toObject(ByteBufferUtil.getByte(in));
		out.close();
		in.close();
		return messageInfo.getResult();
		//		MessageInfo messageInfo = new MessageInfo();
		//		messageInfo.setPath("x/1.0").setMethod("java.lang.String:a");
		//		s = getConnection(null);
		//		OutputStream out = s.getOutputStream();
		//		out.write(ByteBufferUtil.getBuffer(defaultSerializ.serializ(messageInfo)).array());
		//		InputStream in = s.getInputStream();
		//		messageInfo = defaultSerializ.toObject(ByteBufferUtil.getByte(in));
		//		out.close();
		//		in.close();
		//		return messageInfo.getResult();
	}
}
