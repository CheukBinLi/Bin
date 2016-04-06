package com.cheuks.bin.anythingtest.netty.packagemessage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

import io.netty.buffer.ByteBuf;

public class DefaultObjectCoded implements ObjectCodec {

	public <T> T encodeByByte(byte[] o) throws IOException, ClassNotFoundException {
		if (null == o || o.length < 1)
			return null;
		InputStream in = new ByteArrayInputStream(o);
		ObjectInputStream inputStream = new ObjectInputStream(in);
		try {
			Object reault = inputStream.readObject();
			return null == reault ? null : (T) reault;
		} finally {
			inputStream.close();
			in.close();
		}
	}

	public byte[] codeByByte(Object o) throws IOException {
		if (null == o)
			return null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream outputStream = new ObjectOutputStream(out);
		try {
			outputStream.writeObject(o);
			outputStream.flush();
			return out.toByteArray();
		} finally {
			outputStream.close();
			out.close();
		}
	}

	public <T> T encodeByByteBuffer(ByteBuffer o) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T encodeByByteBuf(ByteBuf o) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

	public ByteBuffer codeByByteBuffer(Object o) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

	public ByteBuf codeByByteBuf(Object o) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

}
