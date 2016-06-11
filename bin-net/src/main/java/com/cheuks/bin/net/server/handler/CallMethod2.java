package com.cheuks.bin.net.server.handler;

import com.cheuks.bin.net.util.ByteBufferUtil;
import com.cheuks.bin.net.util.ByteBufferUtil.DataPacket;
import com.cheuks.bin.net.util.DefaultSerializImpl;
import com.cheuks.bin.net.util.Serializ;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class CallMethod2 implements Cloneable {

	private Serializ defaultSerializ;
	private Socket s;
	private String ip;
	private int port;
	private boolean shortConnect;
	private long timeOut;
	private long connectionDateTime;

	public CallMethod2(String ip, int port, boolean shortConnect, int timeOut, Serializ serializ) {
		super();
		this.ip = ip;
		this.port = port;
		this.shortConnect = shortConnect;
		this.timeOut = timeOut;
		//		System.err.println(null == serializ);
		//		if (null != serializ)
		//			this.defaultSerializ = serializ;
		this.defaultSerializ = (null != serializ ? serializ : new DefaultSerializImpl());
	}

	public Socket getConnection() throws IOException {
		try {
//			if (null != s && !shortConnect && !s.isClosed() && timeOutChecked(this.connectionDateTime, this.timeOut)) {
//				System.out.println(s.hashCode());
//				return s;
//			}
			s = new Socket();
			s.connect(new InetSocketAddress(this.ip, this.port));
			return s;
		} finally {
			connectionDateTime = System.currentTimeMillis();
		}

	}

	private boolean timeOutChecked(long lastConnectionTime, long timeOut) {
		return System.currentTimeMillis() - lastConnectionTime < timeOut;
	}

	public Object call(String path, String methodName, Object... params) throws NumberFormatException, Throwable {
		MessageInfo messageInfo = new MessageInfo();
		Socket socket = null;
		OutputStream out = null;
		InputStream in = null;
		try {
			messageInfo.setPath(path).setMethod(methodName);
			messageInfo.setParams(params);
			socket = getConnection();
			out = socket.getOutputStream();
			out.write(ByteBufferUtil.newInstance().createPackageByBytes(DataPacket.SERVICE_TYPE_RMI, DataPacket.CONNECT_TYPE_LONG, defaultSerializ.serializ(messageInfo)));
			out.flush();
			in = socket.getInputStream();
			messageInfo = defaultSerializ.toObject(ByteBufferUtil.newInstance().getData(in, false).getData());
			if (null != messageInfo.getThrowable())
				throw messageInfo.getThrowable();
		} finally {
			if (shortConnect) {
				if (null != out)
					out.close();
				if (null != in)
					in.close();
				socket.close();
				socket = null;
			}
		}
		return messageInfo.getResult();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
