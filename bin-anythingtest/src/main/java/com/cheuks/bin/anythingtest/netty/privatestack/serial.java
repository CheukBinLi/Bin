package com.cheuks.bin.anythingtest.netty.privatestack;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import io.netty.buffer.ByteBuf;

public class serial {

	static final byte[] nullByte = new byte[0];

	public static byte[] objectToBytes(Object o) throws IOException {
		if (null == o)
			return nullByte;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream oout = new ObjectOutputStream(out);
		try {
			oout.writeObject(o);
			oout.flush();
			return out.toByteArray();
		} finally {
			oout.close();
		}
	}

	public static Object byteToOject(byte[] b) throws IOException, ClassNotFoundException {
		if (null == b)
			return null;
		ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(b));
		return in.readObject();
	}

	public static Object byteToOject(ByteBuf b) throws IOException, ClassNotFoundException {
		return byteToOject(b.array());
	}
}
