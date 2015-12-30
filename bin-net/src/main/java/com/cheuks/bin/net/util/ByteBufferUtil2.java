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
public class ByteBufferUtil2 {

	private static int LENGTH_WAY = 16;
	private static String formatChar = "%0" + LENGTH_WAY + "d";
	private int serverType;
	private int connectionType;
	private String handlerPath;

	/***
	 * 设置长度
	 * 
	 * @param size
	 */
	public static void setLENGTH_WAY(int size) {
		LENGTH_WAY = size;
		formatChar = "%0" + size + "d";
	}

	private static final byte[] get(ScatteringByteChannel scatteringByteChannel, int size) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(size);
		scatteringByteChannel.read(buffer);
		buffer.flip();
		return buffer.array();
	}

	private static final Integer getIntegerLen(ScatteringByteChannel scatteringByteChannel, int size) {
		try {
			ByteBuffer buffer = ByteBuffer.allocate(size);
			scatteringByteChannel.read(buffer);
			buffer.flip();
			return Integer.valueOf(new String(buffer.array()));
		} catch (Exception e) {
			return -1;
		}
	}

	public static final ByteArrayOutputStream getByte(ScatteringByteChannel scatteringByteChannel) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(512);
		ByteArrayOutputStream out = null;
		int length = getIntegerLen(scatteringByteChannel, LENGTH_WAY);
		if (length < 0)
			return out;
		out = new ByteArrayOutputStream(length);
		int count = length / buffer.capacity();
		int x = length % buffer.capacity();
		for (int i = 0; i < count; i++) {
			scatteringByteChannel.read(buffer);
			buffer.flip();
			out.write(buffer.array());
			buffer.clear();
		}
		if (x > 0) {
			out.write(get(scatteringByteChannel, x));
		}
		return out;
	}

	public static final ByteArrayOutputStream getByte(InputStream inputStream) throws IOException, NumberFormatException {
		byte[] buffer = new byte[512];
		byte[] lenByte = new byte[LENGTH_WAY];
		inputStream.read(lenByte);
		int length = Integer.valueOf(new String(lenByte));
		ByteArrayOutputStream out = new ByteArrayOutputStream(length);
		int count = length / buffer.length;
		int x = length % buffer.length;
		for (int i = 0; i < count; i++) {
			inputStream.read(buffer);
			out.write(buffer);
		}
		if (x > 0) {
			byte[] temp = new byte[x];
			inputStream.read(temp, 0, x);
			out.write(temp);
		}
		return out;
	}

	public static final ByteBuffer getBuffer(InputStream in, int size) throws IOException, NumberFormatException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(size + LENGTH_WAY);
		byte[] buff = new byte[512];
		int len = -1;
		byteBuffer.put(String.format(formatChar, size).getBytes());
		while ((len = in.read(buff)) > 0) {
			byteBuffer.put(buff, 0, len);
		}
		byteBuffer.flip();
		return byteBuffer;
	}

	public static final ByteBuffer getBuffer(byte[] bytes) throws IOException, NumberFormatException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length + LENGTH_WAY);
		//		String formatChar = "%0" + LENGTH_WAY + "d";
		byteBuffer.put(String.format(formatChar, bytes.length).getBytes()).put(bytes);
		byteBuffer.flip();
		//				 System.err.println(new String(byteBuffer.array()));
		return byteBuffer;
	}

	public static final byte[] getBytes(String str) {
		return getBytes(str.getBytes());
	}

	public static final byte[] getBytes(byte[] bytes) {
		byte[] result = new byte[bytes.length + LENGTH_WAY];
		byte[] length = String.format(formatChar, bytes.length).getBytes();
		for (int i = 0, len = result.length, lenLength = 0, bytesLength = 0, LL = length.length; i < len; i++, lenLength++, bytesLength++) {
			if (i == LL)
				bytesLength = 0;
			if (i < LL) {
				result[i] = length[lenLength];
			}
			else {
				result[i] = bytes[bytesLength];
			}
		}
		return result;

		//				ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length + LENGTH_WAY);
		//				byteBuffer.put(String.format(formatChar, bytes.length).getBytes()).put(bytes);
		//				byteBuffer.flip();
		//				return byteBuffer.array();
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		System.out.println(113 / 6);
		System.out.println(113 % 6);
		System.out.println(113 % 6 == 0);
		System.out.println(107 / 6);
		System.out.println(10 % 3);
		System.out.println(95 % 1024);
		String formatChar = "%0" + LENGTH_WAY + "d";
		String length = String.format(formatChar, 123);
		System.err.println(length);
		System.err.println(length.getBytes().length);
		System.err.println(Integer.MAX_VALUE);
		System.err.println(Long.MAX_VALUE);
		byte[] x = getBytes("你好吗".getBytes());
		System.err.println(new String(x));

		System.err.println("com.cheuks.bin.net.server.event".getBytes().length);
		String a = Integer.toHexString(99999);
		System.err.println(a);
		System.err.println(Integer.parseInt(a, 16));

		String f = "%12s";
		System.err.println(String.format(f, "xxxxx"));
	}

}
