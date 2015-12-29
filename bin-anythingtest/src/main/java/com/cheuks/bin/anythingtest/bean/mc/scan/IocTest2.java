package com.cheuks.bin.anythingtest.bean.mc.scan;

import com.cheuks.bin.annotation.Register;

@Register
public class IocTest2 {

	private AutoLoadTestI a = new AutoLoadTestImpl();

	public void x() {
		a.hello("asdf");
	}

}
