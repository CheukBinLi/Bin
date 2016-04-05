package com.cheuks.bin.anythingtest.netty.packagemessage;

import java.nio.ByteBuffer;

import io.netty.buffer.ByteBuf;

/***
 * *
 * 
 * Copyright 2015    CHEUK.BIN.LI Individual All
 *  
 * ALL RIGHT RESERVED
 *  
 * CREATE ON 2016年4月5日下午3:13:48
 *  
 * EMAIL:20796698@QQ.COM
 *  
 * GITHUB:https://github.com/fdisk123
 * 
 * @author CHEUK.BIN.LI
 * 
 * @see 序列化标准接口
 *
 */
public interface ObjectCoded<T> {

	public T encodeByByte(byte[] o);

	public T encodeByByteBuffer(ByteBuffer o);

	public T encodeByByteBuf(ByteBuf o);

	public byte[] codeByByte(T o);

	public ByteBuffer codeByByteBuffer(T o);

	public ByteBuf codeByByteBuf(T o);

}
