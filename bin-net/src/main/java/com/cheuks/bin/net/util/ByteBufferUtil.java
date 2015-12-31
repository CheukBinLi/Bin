package com.cheuks.bin.net.util;

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
public class ByteBufferUtil {

	/***
	 * 数据结构 SERVICE+CONNECT_TYPE+SERVICE_HANDLE_PATH_LEN+LENGTH_LEN
	 */

	// private static final String formatChar = "%0" + LENGTH_WAY + "d";
	private static final int LENGTH_LEN = 8;
	private static final int SERVICE_LEN = 2;// 服务类型
	private static final int CONNECT_TYPE_LEN = 1;// 长短连接
	private static final int SERVICE_HANDLE_ID_LEN = 8;// 服务类型
	private static final int HEARDER_LEN = LENGTH_LEN + SERVICE_LEN + SERVICE_HANDLE_ID_LEN + CONNECT_TYPE_LEN;// 报头长度

	private static final String HEADER = "%" + SERVICE_LEN + "d%" + CONNECT_TYPE_LEN + "d%" + SERVICE_HANDLE_ID_LEN + "d%" + LENGTH_LEN + "s";
	private static final String LENGTH_FORMAT = "%" + LENGTH_LEN + "s";

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

	public ByteBuffer createPackageByByteBuffer(int serviceType, int connectType, int serviceHandleIdType, byte[] o) {
		return ByteBuffer.wrap(createPackageByBytes(serviceType, connectType, serviceHandleIdType, o));
	}

	public byte[] createPackageByBytes(byte[] o) {
		byte[] data = new byte[LENGTH_LEN + o.length];
		data = insertDate(data, 0, String.format(LENGTH_FORMAT, Integer.toHexString(o.length)).getBytes());
		data = insertDate(data, LENGTH_LEN, o);
		return data;
	}

	public byte[] createPackageByBytes(int serviceType, int connectType, int serviceHandleIdType, byte[] o) {
		byte[] data = new byte[HEARDER_LEN + o.length];
		data = insertDate(data, 0, String.format(HEADER, serviceType, connectType, serviceHandleIdType, Integer.toHexString(o.length)).getBytes());
		data = insertDate(data, HEARDER_LEN, o);
		return data;
	}

	public DataPacket getData(ScatteringByteChannel scatteringByteChannel, boolean header) throws IOException {
		ByteBuffer hearder = ByteBuffer.allocate(HEARDER_LEN);
		DataPacket dataPacket = new DataPacket();
		byte[] temp = new byte[HEARDER_LEN];
		scatteringByteChannel.read(hearder);
		hearder.flip();
		if (header) {
			hearder.get(temp, 0, SERVICE_LEN);
			dataPacket.setServiceType(temp, SERVICE_LEN);

			hearder.get(temp, CONNECT_TYPE_LEN, CONNECT_TYPE_LEN);
			dataPacket.setConnectType(temp, CONNECT_TYPE_LEN);

			hearder.get(temp, SERVICE_LEN + CONNECT_TYPE_LEN, SERVICE_HANDLE_ID_LEN);
			dataPacket.setServiceHandleId(temp, SERVICE_HANDLE_ID_LEN);
		}
		hearder.get(temp, HEARDER_LEN - LENGTH_LEN, LENGTH_LEN);
		dataPacket.setDataLength(temp, LENGTH_LEN);

		ByteBuffer data = ByteBuffer.allocate(dataPacket.getDataLength());
		scatteringByteChannel.read(data);
		return dataPacket.setData(data.array());
	}

	public DataPacket getData(InputStream in, boolean header) throws IOException {
		DataPacket dataPacket = new DataPacket();
		if (header)
			dataPacket.setServiceType(getByte(in, SERVICE_LEN)).setConnectType(getByte(in, CONNECT_TYPE_LEN)).setServiceHandleId(getByte(in, SERVICE_HANDLE_ID_LEN));
		dataPacket.setDataLength(getByte(in, LENGTH_LEN));
		dataPacket.setData(getByte(in, dataPacket.getDataLength()));
		return dataPacket;
	}

	protected byte[] getByte(InputStream in, int size) throws IOException {
		byte[] b = new byte[size];
		in.read(b);
		return b;
	}

	public byte[] createData(DataPacket dataPacket) {
		return createPackageByBytes(dataPacket.getServiceType(), dataPacket.getConnectType(), dataPacket.getServiceHandleId(), dataPacket.getData());
	}

	public byte[] createData(int serviceType, int connectType, int serviceHandleIdType, byte[] data) {
		return createPackageByBytes(serviceType, connectType, serviceHandleIdType, data);
	}

	public byte[] createData(int serviceType, int connectType, int serviceHandleIdType, String data) {
		return createPackageByBytes(serviceType, connectType, serviceHandleIdType, data.getBytes());
	}

	public static class DataPacket {

		public DataPacket() {
			super();
		}

		private int serviceType;
		private int connectType;
		private int serviceHandleId;
		private int dataLength;
		private byte[] data;

		public DataPacket(int serviceType, int connectType, int serviceHandleId, byte[] data) {
			super();
			this.serviceType = serviceType;
			this.connectType = connectType;
			this.serviceHandleId = serviceHandleId;
			this.data = data;
		}

		public int getServiceType() {
			return serviceType;
		}

		public DataPacket setServiceType(int serviceType) {
			this.serviceType = serviceType;
			return this;
		}

		public DataPacket setServiceType(byte[] serviceType, int length) {
			this.serviceType = Integer.valueOf(new String(serviceType, 0, length));
			return this;
		}

		public DataPacket setServiceType(byte[] serviceType) {
			this.serviceType = Integer.valueOf(new String(serviceType));
			return this;
		}

		public int getConnectType() {
			return connectType;
		}

		public DataPacket setConnectType(int connectType) {
			this.connectType = connectType;
			return this;
		}

		public DataPacket setConnectType(byte[] connectType, int length) {
			this.connectType = Integer.valueOf(new String(connectType, 0, length));
			return this;
		}

		public DataPacket setConnectType(byte[] connectType) {
			this.connectType = Integer.valueOf(new String(connectType));
			return this;
		}

		public int getServiceHandleId() {
			return serviceHandleId;
		}

		public DataPacket setServiceHandleId(int serviceHandleId) {
			this.serviceHandleId = serviceHandleId;
			return this;
		}

		public DataPacket setServiceHandleId(byte[] serviceHandleId, int length) {
			this.serviceHandleId = Integer.valueOf(new String(serviceHandleId, 0, length));
			return this;
		}

		public DataPacket setServiceHandleId(byte[] serviceHandleId) {
			this.serviceHandleId = Integer.valueOf(new String(serviceHandleId));
			return this;
		}

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

		public DataPacket setDataLength(byte[] dataLength, int length) {
			this.dataLength = Integer.valueOf(new String(dataLength, 0, length));
			return this;
		}

		public DataPacket setDataLength(byte[] dataLength) {
			this.dataLength = Integer.valueOf(new String(dataLength));
			return this;
		}
	}

	public static void main(String[] args) {
		ByteBufferUtil b = new ByteBufferUtil();
		System.out.println(new String(b.createPackageByBytes(2, 3, 11232, "内容..hjkhkjhjk...   ...!".getBytes())));
		System.out.println(new String(b.createPackageByBytes("内容..hjkhkjhjk...   ...!".getBytes())));
	}

}
