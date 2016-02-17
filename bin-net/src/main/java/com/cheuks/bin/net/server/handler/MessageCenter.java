package com.cheuks.bin.net.server.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;

import com.cheuks.bin.net.util.ByteBufferUtil;
import com.cheuks.bin.net.util.ByteBufferUtil.DataPacket;
import com.cheuks.bin.net.util.Serializ;

public class MessageCenter implements Runnable {

	private static MessageCenter newInstance = new MessageCenter();

	private static ExecutorService executorService = Executors.newCachedThreadPool();

	public static MessageCenter newInstance() {
		return newInstance;
	}

	static BlockingDeque<SocketChannel> socketChannels = new LinkedBlockingDeque<SocketChannel>();

	private MessageCenter() {
	}

	public void run() {

	}

	static synchronized SocketChannel getConnection() throws IOException {
		return SocketChannel.open();

	}

	public MessageInfo working(String ip, int port, MessageInfo messageInfo, Serializ defaultSerializ) throws Throwable {
		Future<MessageInfo> o = executorService.submit(new worker(ip, port, messageInfo, defaultSerializ));

		MessageInfo mi = o.get();
		return mi;	
		//		SocketChannel sc = getConnection();
		//		sc.connect(new InetSocketAddress(ip, port));
		//		sc.write(ByteBufferUtil.newInstance().createPackageByByteBuffer(DataPacket.SERVICE_TYPE_RMI, DataPacket.CONNECT_TYPE_LONG, defaultSerializ.serializ(messageInfo)));
		//		messageInfo = defaultSerializ.toObject(ByteBufferUtil.newInstance().getData(sc, false).getData());
		//		return messageInfo;
	}

	public static class worker implements Callable<MessageInfo> {
		private int port;
		private String ip;
		private Serializ defaultSerializ;
		private MessageInfo messageInfo;

		public worker(String ip, int port, MessageInfo messageInfo, Serializ defaultSerializ) {
			super();
			this.port = port;
			this.ip = ip;
			this.defaultSerializ = defaultSerializ;
			this.messageInfo = messageInfo;
		}

		public MessageInfo call() throws Exception {
			Socket socket = null;
			OutputStream out = null;
			InputStream in = null;
			try {
				socket = new Socket();
				socket.connect(new InetSocketAddress(this.ip, this.port));
				out = socket.getOutputStream();
				out.write(ByteBufferUtil.newInstance().createPackageByBytes(DataPacket.SERVICE_TYPE_RMI, DataPacket.CONNECT_TYPE_LONG, defaultSerializ.serializ(messageInfo)));
				out.flush();
				in = socket.getInputStream();
				messageInfo = defaultSerializ.toObject(ByteBufferUtil.newInstance().getData(in, false).getData());
			} catch (Throwable e) {
				e.printStackTrace();
			} finally {
//				if (null != out)
//					out.close();
//				if (null != in)
//					in.close();
//				socket.close();
//				socket = null;
			}
			return messageInfo;
		}

	}

}
