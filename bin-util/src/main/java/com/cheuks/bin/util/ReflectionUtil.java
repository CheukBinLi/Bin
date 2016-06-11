package com.cheuks.bin.util;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.expr.Handler;

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
		return sb.toString().replaceAll("\\[L|;", "");
	}

	public String getMethodName(CtMethod m) throws NotFoundException {
		StringBuffer sb = new StringBuffer();
		sb.append(m.getReturnType().getName()).append(":").append(m.getName());
		CtClass[] params = m.getParameterTypes();
		for (CtClass cc : params)
			sb.append("@").append(cc.getName());
		return sb.toString().replace("[]", "");
	}

	public Handler aaa(Integer[] a) {
		return null;
	}

	public static void main(String[] args) throws NotFoundException {
		Class<?> cz = ReflectionUtil.class;
		Method[] methodz = cz.getDeclaredMethods();
		for (Method m : methodz)
			System.out.println(ReflectionUtil.newInstance.getMethodName(m));

		ClassPool cp = ClassPool.getDefault();
		CtClass c = cp.get(ReflectionUtil.class.getName());
		CtMethod[] methods = c.getDeclaredMethods();
		for (CtMethod m : methods) {
			System.err.println(ReflectionUtil.newInstance.getMethodName(m));
		}
	}
}
