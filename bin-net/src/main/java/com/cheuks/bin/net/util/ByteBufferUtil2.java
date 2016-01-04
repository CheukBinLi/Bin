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
public class ByteBufferUtil2 implements ConstantType {

	/***
	 * 数据结构 SERVICE+CONNECT_TYPE+SERVICE_HANDLE_PATH_LEN+LENGTH_LEN
	 */

	private static final int LENGTH_LEN = 8;
	private static final int SERVICE_LEN = 2;// 服务类型
	private static final int CONNECT_TYPE_LEN = 1;// 长短连接
	private static final int HEARDER_LEN = LENGTH_LEN + SERVICE_LEN + CONNECT_TYPE_LEN;// 报头长度

	private static final String HEADER = "%" + SERVICE_LEN + "d%" + CONNECT_TYPE_LEN + "d%" + LENGTH_LEN + "s";
	private static final String LENGTH_FORMAT = "%" + LENGTH_LEN + "s";

	private static ByteBufferUtil2 newInstance = new ByteBufferUtil2();

	public static ByteBufferUtil2 newInstance() {
		return newInstance;
	}

	private ByteBufferUtil2() {
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
		if (null != o)
			return ByteBuffer.wrap(createPackageByBytes(o));
		return ByteBuffer.allocate(0);
	}

	public ByteBuffer createPackageByByteBuffer(int serviceType, int connectType/* , int serviceHandleIdType */, byte[] o) {
		return ByteBuffer.wrap(createPackageByBytes(serviceType, connectType, o));
	}

	public byte[] createPackageByBytes(byte[] o) {
		byte[] data = new byte[LENGTH_LEN + o.length];
		data = insertDate(data, 0, String.format(LENGTH_FORMAT, Integer.toHexString(o.length)).getBytes());
		data = insertDate(data, LENGTH_LEN, o);
		return data;
	}

	public byte[] createPackageByBytes(int serviceType, int connectType, /* int serviceHandleIdType, */byte[] o) {
		byte[] data = new byte[HEARDER_LEN + o.length];
		//		data = insertDate(data, 0, String.format(HEADER, serviceType, connectType, serviceHandleIdType, Integer.toHexString(o.length)).getBytes());
		data = insertDate(data, 0, String.format(HEADER, serviceType, connectType, Integer.toHexString(o.length)).getBytes());
		data = insertDate(data, HEARDER_LEN, o);
		return data;
	}

	public DataPacket getData(ScatteringByteChannel scatteringByteChannel, boolean hasHeader) throws IOException {
		ByteBuffer hearder = ByteBuffer.allocate(hasHeader ? HEARDER_LEN : LENGTH_LEN);
		DataPacket dataPacket = new DataPacket();
		//		byte[] temp = new byte[hasHeader ? HEARDER_LEN : LENGTH_LEN];
		byte[] tempHeader = hearder.array();
		byte[] temp;
		scatteringByteChannel.read(hearder);
		hearder.flip();
		if (hasHeader) {
			temp = new byte[SERVICE_LEN];
			System.arraycopy(tempHeader, 0, temp, 0, SERVICE_LEN);
			dataPacket.setServiceType(temp);
			temp = new byte[CONNECT_TYPE_LEN];
			System.arraycopy(tempHeader, SERVICE_LEN, temp, 0, CONNECT_TYPE_LEN);
			dataPacket.setConnectType(temp);
			temp = new byte[LENGTH_LEN];
			System.arraycopy(tempHeader, SERVICE_LEN + CONNECT_TYPE_LEN, temp, 0, LENGTH_LEN);
			dataPacket.setDataLength(temp);
		}
		else {
			hearder.get(tempHeader, 0, LENGTH_LEN);
			dataPacket.setDataLength(tempHeader);
		}

		ByteBuffer data = ByteBuffer.allocate(dataPacket.getDataLength());
		scatteringByteChannel.read(data);
		data.flip();
		return dataPacket.setData(data.array());
	}

	public DataPacket getData(InputStream in, boolean hasHeader) throws IOException {
		DataPacket dataPacket = new DataPacket();
		if (hasHeader)
			dataPacket.setServiceType(getByte(in, SERVICE_LEN)).setConnectType(getByte(in, CONNECT_TYPE_LEN));
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
		private int dataLength;
		private byte[] data;

		public DataPacket(int serviceType, int connectType, int serviceHandleId, byte[] data) {
			super();
			this.serviceType = serviceType;
			this.connectType = connectType;
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
}
