package com.cheuks.bin.net.util;

import java.io.*;

public class DefaultSerializImpl implements Serializ {

	@SuppressWarnings("unchecked")
	public <T> T toObject(byte[] b) throws IOException, ClassNotFoundException {
		T t = null;
		ByteArrayInputStream in = new ByteArrayInputStream(b);
		ObjectInputStream objectInputStream = new ObjectInputStream(in);
		t = (T) objectInputStream.readObject();
		objectInputStream.close();
		in.close();
		return t;
	}

	public byte[] serializ(Serializable obj) throws IOException {
		if (obj == null) {
			return null;
		}
		byte[] b = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(out);
		oos.writeObject(obj);
		b = out.toByteArray();
		oos.close();
		out.close();
		return b;
	}

	public <T> T toObject(ByteArrayOutputStream out) throws Throwable {
		return toObject(out.toByteArray());
	}

}
