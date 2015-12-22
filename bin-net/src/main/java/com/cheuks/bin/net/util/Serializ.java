package com.cheuks.bin.net.util;

import java.io.InputStream;
import java.io.Serializable;

public interface Serializ {

	<T> T toObject(Byte[] b);

	byte[] serializ(Serializable obj);

	byte[] serializ(InputStream in);

}
