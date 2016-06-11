package com.cheuks.bin.net.util;

import java.io.IOException;
import java.util.Arrays;

public class DataFormat {

	public static byte[] createData(byte[] data) throws IOException {
		int length = data.length;
		byte[] result = Arrays.copyOf(data, length + 2);
		result[length] = 10;
		result[length + 1] = 13;
		return result;
	}

	public static void main(String[] args) throws IOException {
		String a = "你好吗？";
		byte[] b0 = a.getBytes();
		byte[] b1 = createData(a.getBytes());
		System.out.print(new String(b1));
		System.out.print("a");
	}

}
