package com.cheuks.bin.anythingtest.nio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ScatteringByteChannel;

/***
 * 16位长度
 * @author hnbh
 *
 */
public class ByteBufferUtil {

	private static final int LENGTH_WAY = 16;

	private static final byte[] get(ScatteringByteChannel scatteringByteChannel, int size) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(size);
		scatteringByteChannel.read(buffer);
		buffer.flip();
		// System.out.println(new String(buffer.array()));
		return buffer.array();
	}

	public static final ByteArrayOutputStream getByte(ScatteringByteChannel scatteringByteChannel) {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		ByteArrayOutputStream out = null;
		try {
			// scatteringByteChannel.read(buffer, 0, LENGTH_WAY);
			// byte[] lenByte = new byte[LENGTH_WAY];
			byte[] lenByte = get(scatteringByteChannel, LENGTH_WAY);
			int length = Integer.valueOf(new String(lenByte));
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
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}

	public static final ByteArrayOutputStream getByte(InputStream inputStream) throws IOException {
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

	public static final ByteBuffer getBuffer(InputStream in, int size) throws IOException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(size + LENGTH_WAY);
		byte[] buff = new byte[512];
		int len = -1;
		String formatChar = "%0" + LENGTH_WAY + "d";
		byteBuffer.put(String.format(formatChar, size).getBytes());
		while ((len = in.read(buff)) > 0) {
			byteBuffer.put(buff, 0, len);
		}
		byteBuffer.flip();
		return byteBuffer;
	}

	public static final ByteBuffer getBuffer(byte[] bytes) throws IOException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length + LENGTH_WAY);
		String formatChar = "%0" + LENGTH_WAY + "d";
		byteBuffer.put(String.format(formatChar, bytes.length).getBytes()).put(bytes);
		byteBuffer.flip();
		//		System.err.println(new String(byteBuffer.array()));
		return byteBuffer;
	}

	public byte[] getBytes(String str) throws IOException {
		byte[] bytes = str.getBytes();
		ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length + LENGTH_WAY);
		String formatChar = "%0" + LENGTH_WAY + "d";
		byteBuffer.put(String.format(formatChar, bytes.length).getBytes()).put(bytes);
		//		System.out.println("byte[] getBytes(String str)" + new String(byteBuffer.array()));
		byteBuffer.flip();
		return byteBuffer.array();
	}

	public byte[] getBytes(byte[] bytes) throws IOException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length + LENGTH_WAY);
		String formatChar = "%0" + LENGTH_WAY + "d";
		byteBuffer.put(String.format(formatChar, bytes.length).getBytes()).put(bytes);
		// System.out.println("byte[] getBytes(byte[] bytes)" + new String(byteBuffer.array()));
		byteBuffer.flip();
		return byteBuffer.array();
	}

	public static void main(String[] args) {
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
	}

}
