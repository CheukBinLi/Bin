package com.cheuks.bin.net.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DefaultSerializ implements Serializ {

	protected DefaultSerializ() throws IOException, SecurityException {
		super();
	}

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

	public byte[] serializ(InputStream in) {
		return;
	}

}
