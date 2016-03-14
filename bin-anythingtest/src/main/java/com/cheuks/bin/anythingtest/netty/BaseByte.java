package com.cheuks.bin.anythingtest.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class BaseByte {

	public static ByteBuf getByteBuf(byte[] b) {
		ByteBuf bb = Unpooled.buffer(b.length);
		bb.writeBytes(b);
		return bb;
	}

}
