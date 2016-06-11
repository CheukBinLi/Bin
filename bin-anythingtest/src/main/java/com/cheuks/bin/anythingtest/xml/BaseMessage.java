package com.cheuks.bin.anythingtest.xml;

import java.beans.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/***
 * 前置接口对象基类
 * <p>
 * 头文件对象一定要实现以实现自动插入接口交易码<setTransactionCode(String code)> 方法
 * 
 * @author Ben-Book
 *
 */
public abstract class BaseMessage {

	/***
	 * 写入前置交易码 头文件对象必须实现的方法
	 * 
	 * @param code
	 */
	public void setTransactionCode(String code) {
	}

	private MessageHeader HEAD;

	public MessageHeader getHEAD() {
		return HEAD;
	}

	public BaseMessage setHEAD(MessageHeader hEAD) {
		HEAD = hEAD;
		return this;
	}

	/***
	 * 字段缓存对象
	 */

	protected static Map<String, List<Field>> FIELDS = new HashMap<String, List<Field>>();

	/***
	 * 生成 每个字段的XML标签
	 * 
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException 
	 */
	protected String buildXml() throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		//		// public String buildXml() throws IllegalArgumentException,
		//		// IllegalAccessException {
		//		List<Field> field = FIELDS.get(this.getClass().getName());
		//		if (field == null)
		//			field = markField();
		//		StringBuffer sb = new StringBuffer();
		//		Object value;
		//		for (Field f : field) {
		//			value = f.get(this);
		//			// System.err.println(getName);
		//			// 这里去掉了回车 zhouhx 0413
		//			sb.append("<" + f.getName().toUpperCase() + ">").append(null == value ? "" : value).append("</" + f.getName().toUpperCase() + ">")/*
		//																																			 */;
		//		}
		//		// 生成当前对象xml字符
		//		return sb.toString();
		return sub(this);
	}

	protected String sub(Object o) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		if (isGeneralType(o.getClass()))
			return o.toString();
		Field[] fields = o.getClass().getDeclaredFields();
		Object tempO;
		StringBuffer sb = new StringBuffer();
		for (Field f : fields) {
			if (null != f.getAnnotation(Transient.class))
				continue;
			f.setAccessible(true);
			sb.append("<").append(f.getName()).append(">").append(sub(null == (tempO = f.get(o)) ? f.getType().newInstance() : tempO)).append("</").append(f.getName()).append(">");
		}
		return sb.toString();
	}

	protected boolean isGeneralType(Class<?> c) {
		if (!c.isArray() && (c == int.class || c == Integer.class || c == String.class || c == char.class || c == Character.class || c == short.class | c == Short.class || c == long.class || c == Long.class || c == float.class || c == Float.class || c == byte.class || c == Byte.class || c == double.class || c == Double.class))
			return true;
		return false;
	}

	/***
	 * 筛选字段
	 * 
	 * @return
	 */
	protected List<Field> markField() {
		List<Field> field = new ArrayList<Field>();
		Field[] fields = this.getClass().getDeclaredFields();
		Method[] methods = this.getClass().getDeclaredMethods();
		Set<String> ms = new HashSet<String>();
		for (Method m : methods) {
			ms.add(m.getName());
			// System.out.println(m.getName());
		}
		String getName;
		for (Field f : fields) {
			getName = "get" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
			// System.err.println(getName);
			if (null == f.getAnnotation(Transient.class) && ms.contains(getName)) {
				f.setAccessible(true);
				field.add(f);
			}

		}
		FIELDS.put(this.getClass().getName(), field);
		return field;
	}

}
