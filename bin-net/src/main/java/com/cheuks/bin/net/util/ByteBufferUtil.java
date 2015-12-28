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
public class ByteBufferUtil {

	private static int LENGTH_WAY = 16;
	private static String formatChar = "%0" + LENGTH_WAY + "d";

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

	public byte[] getBytes(String str) throws IOException {
		byte[] bytes = str.getBytes();
		ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length + LENGTH_WAY);
		byteBuffer.put(String.format(formatChar, bytes.length).getBytes()).put(bytes);
		byteBuffer.flip();
		return byteBuffer.array();
	}

	public byte[] getBytes(byte[] bytes) throws IOException, NumberFormatException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length + LENGTH_WAY);
		byteBuffer.put(String.format(formatChar, bytes.length).getBytes()).put(bytes);
		byteBuffer.flip();
		return byteBuffer.array();
	}

}
