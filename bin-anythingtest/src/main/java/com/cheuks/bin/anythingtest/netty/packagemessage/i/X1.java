package com.cheuks.bin.anythingtest.netty.packagemessage.i;

import com.cheuks.bin.anythingtest.netty.packagemessage.Point;

@Point(UID = "cbd")
public class X1 implements X {

	/*
	 * (non-Javadoc)
	 * @see com.cheuks.bin.anythingtest.netty.packagemessage.i.X#A()
	 */
	public String A() {
		return "this is A";
	}

	/*
	 * (non-Javadoc)
	 * @see com.cheuks.bin.anythingtest.netty.packagemessage.i.X#A(java.lang.String)
	 */
	public String A(String str) {
		return "this is A:" + str;
	}

	/*
	 * (non-Javadoc)
	 * @see com.cheuks.bin.anythingtest.netty.packagemessage.i.X#A(double)
	 */
	public String A(double str) {
		return "this is A:" + str;
	}

	/*
	 * (non-Javadoc)
	 * @see com.cheuks.bin.anythingtest.netty.packagemessage.i.X#A(java.lang.String, double)
	 */
	public String A(String str, double str1) {
		return "this is A:" + str + str1;
	}

}
