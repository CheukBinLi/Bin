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
public interface ObjectCodec {

	public <T> T encodeByByte(byte[] o) throws Throwable;

	public <T> T encodeByByteBuffer(ByteBuffer o) throws Throwable;

	public <T> T encodeByByteBuf(ByteBuf o) throws Throwable;

	public byte[] codeByByte(Object o) throws Throwable;

	public ByteBuffer codeByByteBuffer(Object o) throws Throwable;

	public ByteBuf codeByByteBuf(Object o) throws Throwable;

}
