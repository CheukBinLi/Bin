package com.cheuks.bin.net.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ScatteringByteChannel;

/***
 * *
 * 
 * Copyright 2015 ZHOU.BING.LI Individual All
 * 
 * ALL RIGHT RESERVED
 * 
 * CREATE ON 2015年12月7日下午5:02:52
 * 
 * EMAIL:20796698@QQ.COM
 * 
 * GITHUB:https://github.com/fdisk123
 * 
 * @author CHEUK.BIN.LI
 * 
 * @see 块/字节转换工具
 *
 */
public class ByteBufferUtil implements ConstantType {

	/***
	 * 数据结构 SERVICE+CONNECT_TYPE+SERVICE_HANDLE_PATH_LEN
	 */

	private static final int SERVICE_LEN = 2;// 服务类型
	private static final int CONNECT_TYPE_LEN = 1;// 长短连接
	private static final byte[] END = new byte[] { 10, 13 };
	private static final int LENGTH = SERVICE_LEN + CONNECT_TYPE_LEN + END.length;
	private static final int HEADER_LENGTH = SERVICE_LEN + CONNECT_TYPE_LEN;

	//	private static final byte[] EMPTY_HEADER = new byte[SERVICE_LEN + CONNECT_TYPE_LEN];

	private static final String HEADER = "%" + SERVICE_LEN + "d%" + CONNECT_TYPE_LEN + "d";

	private static ByteBufferUtil newInstance = new ByteBufferUtil();

	public static ByteBufferUtil newInstance() {
		return newInstance;
	}

	//	public int getLength(ScatteringByteChannel scatteringByteChannel, int size) throws IOException {
	//		ByteBuffer len = ByteBuffer.allocate(size);
	//		scatteringByteChannel.read(len);
	//		len.flip();
	//		return Integer.valueOf(new String(len.array()).trim());
	//	}

	private ByteBufferUtil() {
		super();
	}

	/***
	 * 
	 * @param data
	 *            主数据
	 * @param seek
	 *            偏移
	 * @param b
	 *            待插入数据
	 * @return 主数据
	 */
	protected byte[] insertDate(byte[] data, int seek, byte[] b) {
		for (int i = 0, len = b.length; i < len; i++, seek++)
			data[seek] = b[i];
		return data;
	}

	public ByteBuffer createPackageByByteBuffer(byte[] o) {
		return ByteBuffer.wrap(createPackageByBytes(o));
	}

	public ByteBuffer createPackageByByteBuffer(int serviceType, int connectType/* , int serviceHandleIdType */, byte[] o) {
		return ByteBuffer.wrap(createPackageByBytes(serviceType, connectType, o));
	}

	public byte[] createPackageByBytes(byte[] o) {
		if (null == o)
			o = new byte[0];
		byte[] data = new byte[END.length + o.length];
		//		System.arraycopy(EMPTY_HEADER, 0, data, 0, EMPTY_HEADER.length);
		System.arraycopy(o, 0, data, 0, o.length);
		System.arraycopy(END, 0, data, data.length - 2, END.length);
		return data;
	}

	public byte[] createPackageByBytes(int serviceType, int connectType, /* int serviceHandleIdType, */byte[] o) {
		byte[] data = new byte[LENGTH + o.length];
		//		//		data = insertDate(data, 0, String.format(HEADER, serviceType, connectType, serviceHandleIdType, Integer.toHexString(o.length)).getBytes());
		//		data = insertDate(data, 0, String.format(HEADER, serviceType, connectType, Integer.toHexString(o.length)).getBytes());
		//		data = insertDate(data, HEARDER_LEN, o);
		System.arraycopy(String.format(HEADER, serviceType, connectType).getBytes(), 0, data, 0, HEADER_LENGTH);
		System.arraycopy(o, 0, data, HEADER_LENGTH, o.length);
		System.arraycopy(END, 0, data, data.length - 2, END.length);
		return data;
	}

	public DataPacket getData(final ScatteringByteChannel scatteringByteChannel, boolean hasHeader) throws IOException {
		DataPacket dataPacket = new DataPacket();
		ByteBuffer buffer = ByteBuffer.allocate(1);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] header = null;
		if (hasHeader)
			header = new byte[HEADER_LENGTH];
		int prv = -1, next, count = 0;
		scatteringByteChannel.read(buffer);
		buffer.flip();
		while (prv != 10 && (next = buffer.array()[0]) != 13 && next != -1) {
			if (prv != -1) {
				out.write(prv);
				if (hasHeader && count < HEADER_LENGTH)
					header[count++] = (byte) prv;
			}
			prv = next;
			scatteringByteChannel.read(buffer);
			buffer.flip();
		}
		//
		byte[] data = new byte[hasHeader ? (out.size() - HEADER_LENGTH) : out.size()];
		//		scatteringByteChannel.read(hearder);
		//		hearder.flip();
		if (hasHeader) {
			byte[] tempHeader = header;
			//			System.arraycopy(data, 0, tempHeader, 0, HEADER_LENGTH);
			byte[] temp;
			temp = new byte[SERVICE_LEN];
			System.arraycopy(tempHeader, 0, temp, 0, SERVICE_LEN);
			dataPacket.setServiceType(temp);
			temp = new byte[CONNECT_TYPE_LEN];
			System.arraycopy(tempHeader, SERVICE_LEN, temp, 0, CONNECT_TYPE_LEN);
			dataPacket.setConnectType(temp);
		}
		dataPacket.setDataLength(out.size() - HEADER_LENGTH);
		//		System.arraycopy(data, 0, out.toByteArray(), hasHeader ? HEADER_LENGTH : 0, data.length);
		System.arraycopy(out.toByteArray(), hasHeader ? HEADER_LENGTH : 0, data, 0, data.length);
		return dataPacket.setData(data);
	}

	public DataPacket getData(final InputStream in, boolean hasHeader) throws IOException {
		DataPacket dataPacket = new DataPacket();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] header = null;
		if (hasHeader)
			header = new byte[HEADER_LENGTH];
		int prv = -1, next, count = 0;
		while (prv != 10 && (next = in.read()) != 13 && next != -1) {
			if (prv != -1) {
				out.write(prv);
				if (hasHeader && count < HEADER_LENGTH)
					header[count++] = (byte) prv;
			}
			prv = next;
		}
		//
		byte[] data = new byte[hasHeader ? (out.size() - HEADER_LENGTH) : out.size()];
		if (hasHeader) {
			byte[] tempHeader = header;
			//			System.arraycopy(data, 0, tempHeader, 0, HEADER_LENGTH);
			byte[] temp;
			temp = new byte[SERVICE_LEN];
			System.arraycopy(tempHeader, 0, temp, 0, SERVICE_LEN);
			dataPacket.setServiceType(temp);
			temp = new byte[CONNECT_TYPE_LEN];
			System.arraycopy(tempHeader, SERVICE_LEN, temp, 0, CONNECT_TYPE_LEN);
			dataPacket.setConnectType(temp);
		}
		dataPacket.setDataLength(out.size() - HEADER_LENGTH);
		//		System.arraycopy(data, 0, out.toByteArray(), hasHeader ? HEADER_LENGTH : 0, data.length);
		System.arraycopy(out.toByteArray(), hasHeader ? HEADER_LENGTH : 0, data, 0, data.length);
		return dataPacket.setData(data);
	}

	public byte[] createData(DataPacket dataPacket) {
		//		return createPackageByBytes(dataPacket.getServiceType(), dataPacket.getConnectType(), dataPacket.getServiceHandleId(), dataPacket.getData());
		return createPackageByBytes(dataPacket.getServiceType(), dataPacket.getConnectType(), dataPacket.getData());
	}

	public byte[] createData(int serviceType, int connectType/* , int serviceHandleIdType */, byte[] data) {
		return createPackageByBytes(serviceType, connectType, data);
	}

	public byte[] createData(int serviceType, int connectType/* , int serviceHandleIdType */, String data) {
		return createPackageByBytes(serviceType, connectType, data.getBytes());
	}

	public static class DataPacket implements ConstantType {

		public DataPacket() {
			super();
		}

		private int serviceType;
		private int connectType;
		//		private int serviceHandleId;
		private int dataLength;
		private byte[] data;

		public DataPacket(int serviceType, int connectType, int serviceHandleId, byte[] data) {
			super();
			this.serviceType = serviceType;
			this.connectType = connectType;
			//			this.serviceHandleId = serviceHandleId;
			this.data = data;
		}

		public int getServiceType() {
			return serviceType;
		}

		public DataPacket setServiceType(int serviceType) {
			this.serviceType = serviceType;
			return this;
		}

		public DataPacket setServiceType(byte[] serviceType) {
			this.serviceType = Integer.valueOf(new String(serviceType).trim());
			return this;
		}

		public int getConnectType() {
			return connectType;
		}

		public DataPacket setConnectType(int connectType) {
			this.connectType = connectType;
			return this;
		}

		public DataPacket setConnectType(byte[] connectType) {
			this.connectType = Integer.valueOf(new String(connectType).trim());
			return this;
		}

		//		public int getServiceHandleId() {
		//			return serviceHandleId;
		//		}
		//
		//		public DataPacket setServiceHandleId(int serviceHandleId) {
		//			this.serviceHandleId = serviceHandleId;
		//			return this;
		//		}
		//
		//		public DataPacket setServiceHandleId(byte[] serviceHandleId, int length) {
		//			this.serviceHandleId = Integer.valueOf(new String(serviceHandleId, 0, length));
		//			return this;
		//		}
		//
		//		public DataPacket setServiceHandleId(byte[] serviceHandleId) {
		//			this.serviceHandleId = Integer.valueOf(new String(serviceHandleId));
		//			return this;
		//		}

		public byte[] getData() {
			return data;
		}

		public DataPacket setData(byte[] data) {
			this.data = data;
			return this;
		}

		public int getDataLength() {
			return dataLength;
		}

		public DataPacket setDataLength(int dataLength) {
			this.dataLength = dataLength;
			return this;
		}

		public DataPacket setDataLength(byte[] dataLength) {
			this.dataLength = Integer.parseInt(new String(dataLength).trim(), 16);
			return this;
		}
	}

	public static void main(String[] args) {
		ByteBufferUtil b = new ByteBufferUtil();
		System.out.println(new String(b.createPackageByBytes(2, 3, "内容..hjkhkjhjk...   ...!".getBytes())));
		//		System.out.println(new String(b.createPackageByBytes("内容..hjkhkjhjk...   ...!".getBytes())));

		System.out.println(new String(b.createPackageByBytes("内容..hjkhkjhjk...   ...!".getBytes())));

		//		byte[] a = "你好吗？".getBytes();
		//		byte[] x = "哇哈哈".getBytes();
		//		System.out.println(x.length);
		//		byte[] result = new byte[a.length + x.length];
		//		System.arraycopy(x, 0, result, 0, x.length);
		//		System.out.println(new String(result));
		//		System.arraycopy(a, 0, result, x.length, a.length);
		//		System.out.println(new String(result));
		//		int a1 = '\n';
		//		int a2 = '\r';
		//		System.out.println(a1 + " " + a2);
	}
}
