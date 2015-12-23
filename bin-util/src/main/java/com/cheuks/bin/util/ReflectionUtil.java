package com.cheuks.bin.util;

import java.lang.reflect.Method;

public class ReflectionUtil {
	private ReflectionUtil() {
	}

	private static ReflectionUtil newInstance = new ReflectionUtil();

	public static ReflectionUtil newInstance() {
		return newInstance;
	}

	public String getMethodName(Method m) {
		StringBuffer sb = new StringBuffer();

		sb.append(m.getReturnType().getName()).append(":").append(m.getName());
		for (Class<?> c : m.getParameterTypes()) {
			sb.append("@").append(c.getName());
		}
		return sb.toString();
	}
}
