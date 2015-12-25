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
import com.cheuks.bin.util.ReflectionUtil;

public class CallMethod {

	protected SocketChannel channel;
	protected static Serializ defaultSerializ = new DefaultSerializImpl();
	protected Socket s;
	SocketChannel sc;
	private String ip;
	private int port;

	public CallMethod(String ip, int port) {
		super();
		this.ip = ip;
		this.port = port;
	}

	public SocketChannel getConnection() throws IOException {
		SocketChannel sc = SocketChannel.open();
		sc.connect(new InetSocketAddress(this.ip, this.port));
		return sc;
	}

	public Object call(String path, String methodName, Object... params) throws NumberFormatException, Throwable {
		MessageInfo messageInfo = new MessageInfo();
		messageInfo.setPath(path).setMethod(methodName);
		messageInfo.setParams(params);
		sc = getConnection();
		sc.write(ByteBufferUtil.getBuffer(defaultSerializ.serializ(messageInfo)));
			messageInfo = defaultSerializ.toObject(ByteBufferUtil.getByte(sc));
		try {
				//"callMethod.call(\"" + this.a.classID() + "\",\"" + ReflectionUtil.newInstance().getMethodName(ctMethod) + "\"," + (ctMethod.getParameterTypes().length > 0 ? "new java.lang.Object[] {$args});" : "null);"
		} catch (java.lang.Throwable e) {e.printStackTrace();}
		//		OutputStream out = s.getOutputStream();
		//		out.write(ByteBufferUtil.getBuffer(defaultSerializ.serializ(messageInfo)).array());
		//		InputStream in = s.getInputStream();
		//		messageInfo = defaultSerializ.toObject(ByteBufferUtil.getByte(in));
		//		out.close();
		//		in.close();
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
