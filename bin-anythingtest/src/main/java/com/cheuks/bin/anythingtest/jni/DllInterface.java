package com.cheuks.bin.anythingtest.jni;

public class DllInterface {

	public native int FindWindowEx(int hwndParent, int hwndChildAfter, String lpszClass, String lpszWindow);

	public native int FindWindow(String lpClassName, String lpWindowName);

	public native boolean SetWindowText(int hwnd, String lpString);

	public native boolean MoveWindow(int hWnd, int X, int Y, int nWidth, int nHeight, boolean bRepaint);

}
