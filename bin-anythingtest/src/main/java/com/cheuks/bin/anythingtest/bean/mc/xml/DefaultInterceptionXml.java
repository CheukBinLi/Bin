package com.cheuks.bin.anythingtest.bean.mc.xml;

import com.cheuks.bin.bean.classprocessing.handler.Interception;

import java.lang.reflect.Method;

public class DefaultInterceptionXml implements Interception {

	public boolean Intercept(Object arg, Method method, Object... params) {
		System.err.println("XML_Intercept method:" + method.getName() + " params:" + params);
		return true;
	}

	public void before() {
		System.err.println("before");
	}

	public void after() {

		System.err.println("after");
	}

}
