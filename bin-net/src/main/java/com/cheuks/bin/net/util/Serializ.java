package com.cheuks.bin.net.util;

import java.io.InputStream;
import java.io.Serializable;

public interface Serializ {

	<T> T toObject(byte[] b) throws Throwable;

	byte[] serializ(Serializable obj) throws Throwable;

	byte[] serializ(InputStream in) throws Throwable;

}
