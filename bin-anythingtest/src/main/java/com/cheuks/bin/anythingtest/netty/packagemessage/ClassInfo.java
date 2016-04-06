package com.cheuks.bin.anythingtest.netty.packagemessage;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cheuks.bin.anythingtest.netty.packagemessage.i.X1;

public class ClassInfo<T> {

	private final Class<T> classFile;
	private String uid;
	private T obj;
	private final Map<Integer, Method> methods = new ConcurrentHashMap<Integer, Method>();

	public ClassInfo(Class<T> classFile) throws InstantiationException, IllegalAccessException {
		super();
		this.classFile = classFile;
		initClass(classFile);
	}

	private void initClass(Class<T> t) throws InstantiationException, IllegalAccessException {
		Point p = t.getDeclaredAnnotation(Point.class);
		if (null == p)
			return;
		this.uid = String.format("%s:%s", p.UID(), p.VERSION());
		Method[] methods = t.getDeclaredMethods();
		for (Method m : methods)
			this.methods.put(getMethodName(m).hashCode(), m);
		obj = t.newInstance();
	}

	private String getMethodName(Method m) {
		StringBuffer sb = new StringBuffer();
		sb.append(m.getReturnType().getName()).append(":").append(m.getName());
		for (Class<?> c : m.getParameterTypes()) {
			sb.append("@").append(c.getName());
		}
		System.out.println(sb.toString().replaceAll("\\[L|;", ""));
		return sb.toString().replaceAll("\\[L|;", "");
	}

	public Class<T> getClassFile() {
		return classFile;
	}

	public T getObj() {
		return obj;
	}

	public String getUid() {
		return uid;
	}

	public ClassInfo<T> setObj(T obj) {
		this.obj = obj;
		return this;
	}

	public Map<Integer, Method> getMethods() {
		return methods;
	}

	public Method getMethod(Object o) {
		return methods.get(Integer.valueOf(o.toString()));
	}

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		ClassInfo<?> a = new ClassInfo(X1.class);
		System.err.println(a.getUid());
		for (Map.Entry<Integer, Method> en : a.methods.entrySet())
			System.err.println(en.getKey().hashCode());

	}

}
