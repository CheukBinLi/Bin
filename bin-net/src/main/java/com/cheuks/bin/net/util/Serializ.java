package com.cheuks.bin.net.util;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public interface Serializ {

	<T> T toObject(byte[] b) throws Throwable;

	<T> T toObject(ByteArrayOutputStream out) throws Throwable;

	byte[] serializ(Serializable obj) throws Throwable;

}
