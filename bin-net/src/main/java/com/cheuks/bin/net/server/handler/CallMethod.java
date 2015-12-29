package com.cheuks.bin.net.server.handler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

import com.cheuks.bin.net.util.ByteBufferUtil;
import com.cheuks.bin.net.util.DefaultSerializImpl;
import com.cheuks.bin.net.util.Serializ;

public class CallMethod {

	protected SocketChannel channel;
	protected static Serializ defaultSerializ = new DefaultSerializImpl();
	protected Socket s;
	SocketChannel sc;
	private String ip;
	private int port;
	private boolean shortConnect;

	public CallMethod(String ip, int port, boolean shortConnect) {
		super();
		this.ip = ip;
		this.port = port;
		this.shortConnect = shortConnect;
	}

	public SocketChannel getConnection() throws IOException {
		if (null != sc && sc.isConnected() && sc.isOpen() && !sc.socket().isClosed())
			return sc;
		sc = SocketChannel.open();
		sc.connect(new InetSocketAddress(this.ip, this.port));
		return sc;
	}

	public Object call(String path, String methodName, Object... params) throws NumberFormatException, Throwable {
		try {
			MessageInfo messageInfo = new MessageInfo();
			messageInfo.setPath(path).setMethod(methodName);
			messageInfo.setParams(params);
			sc = getConnection();
			sc.write(ByteBufferUtil.getBuffer(defaultSerializ.serializ(messageInfo)));
			messageInfo = defaultSerializ.toObject(ByteBufferUtil.getByte(sc));
			if (null != messageInfo.getThrowable())
				throw messageInfo.getThrowable();
			return messageInfo.getResult();
		} finally {
			if (shortConnect && sc.isOpen())
				sc.close();
		}
	}
}
