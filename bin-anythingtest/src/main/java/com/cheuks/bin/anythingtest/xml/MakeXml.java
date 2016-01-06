package com.cheuks.bin.anythingtest.xml;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.cheuks.bin.anythingtest.xml.XmlEntity.B;

public class MakeXml {

	public String r() {
		return null;
	}

	private List<String> oooo = new ArrayList<String>();

	public void x(Object o) {
		Class c = o.getClass();
		System.out.println(c.getName());

		String[] a = new String[0];
		System.err.println(a.getClass());
	}

	public boolean isGeneralType(Class<?> c) {
		if (!c.isArray() && (c == int.class || c == Integer.class || c == String.class || c == char.class || c == Character.class || c == short.class | c == Short.class || c == long.class || c == Long.class || c == float.class || c == Float.class || c == byte.class || c == Byte.class || c == double.class || c == Double.class))
			return true;
		return false;
	}

	public String sub(Object o) throws IllegalArgumentException, IllegalAccessException {
		if (isGeneralType(o.getClass()))
			return o.toString();
		Field[] fields = o.getClass().getDeclaredFields();
		StringBuffer sb = new StringBuffer();
		for (Field f : fields) {
			f.setAccessible(true);
			sb.append("<").append(f.getName()).append(">").append(sub(f.get(o))).append("</").append(f.getName()).append(">");
		}
		return sb.toString();
	}

	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		MakeXml mx = new MakeXml();
		//		Field f = MakeXml.class.getDeclaredField("oooo");
		//		Object o = f.get(mx);
		//		List ox = (List) o;
		//		ox.add("哇哈哈");
		//		ox.add("1");
		//		System.err.println(mx.oooo.get(0));
		//		System.err.println(new String(mx.oooo.get(1)));
		//		for (Class c : o.getClass().getInterfaces())
		//			if (c == List.class)
		//				System.err.println(c.getName());
		XmlEntity xe = new XmlEntity();
		xe.setA("aaaaa").setB("bbbbb").setBclass(new B()).setA("A").setB("B");
		System.err.println(mx.sub(xe));
	}

}
