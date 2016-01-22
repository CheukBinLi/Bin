package com.cheuks.bin.anythingtest.jni;

public class DllTest {

	public static void main(String[] args) {

		String h = System.getProperty("java.library.path");
		System.out.println(ClassLoader.getSystemClassLoader().getResource("").toString().substring(6));
		System.setProperty("java.library.path", ClassLoader.getSystemClassLoader().getResource("").toString().substring(6)+";"+h);
		System.out.println(System.getProperty("java.library.path"));
		System.load(ClassLoader.getSystemClassLoader().getResource("").toString().substring(6)+"\\winapi.dll");
		
		DllInterface dllInterface=new DllInterface();
		System.out.println(dllInterface.FindWindowEx(0, 0, null, "Debug"));
		//System.out.println(dllInterface.FindWindow("cmd", null));
		
	}

}
