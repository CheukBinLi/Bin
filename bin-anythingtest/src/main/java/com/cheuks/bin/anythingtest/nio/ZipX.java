package com.cheuks.bin.anythingtest.nio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipX {

	public static void main(String[] args) throws FileNotFoundException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipInputStream inputStream = new ZipInputStream(new ByteArrayInputStream(null));
		ZipOutputStream outputStream = new ZipOutputStream(null);

		DeflaterInputStream deflaterInputStream = new DeflaterInputStream(new FileInputStream("C:/Users/Ben/Desktop/1.exe"));
		Deflater deflater = new Deflater(1);
		byte[] b = "小明小明小明小明小明小明小明小明小明小明小明小明小明小明小明小明小明小明小明小明小明小明小明小明小明小明小明小明小明小明小明小明小明sdfsdf小明小明小明".getBytes();
		byte[]output=new byte[b.length];
		deflater.setInput(b);
		deflater.deflate(output);
		deflater.finish();
	}

}
