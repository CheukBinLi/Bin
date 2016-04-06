package com.cheuks.bin.anythingtest.netty.packagemessage.i;

import com.cheuks.bin.anythingtest.netty.packagemessage.Point;

@Point(UID = "cbd")
public interface X {

	String A();

	String A(String str);

	String A(double str);

	String A(String str, double str1);

}