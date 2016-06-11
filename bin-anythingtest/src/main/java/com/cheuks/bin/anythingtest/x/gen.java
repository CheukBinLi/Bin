package com.cheuks.bin.anythingtest.x;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class gen {
	public static void main(String args[]) throws IOException {
		FileInputStream fin = new FileInputStream("D:/Desktop/1.exe"); // 可以将整个exe文件解码
		//		FileOutputStream fout = new FileOutputStream(args[1]);
		BufferedInputStream bin = new BufferedInputStream(fin);
		FileOutputStream bout = new FileOutputStream("D:/Desktop/1.jar");
		//		BufferedOutputStream bout = new BufferedOutputStream(fout);
		int in = 0;
		do {
			in = bin.read();
			if (in == -1)
				break;
			in ^= 0x88;
			bout.write(in);
		} while (true);
		bin.close();
		fin.close();
		bout.close();
		//		fout.close();
	}
}