package com.cheuks.bin.registercenter;

import java.util.StringTokenizer;

public abstract class AbstractRegister<T, CreateModel> implements Register<CreateModel> {

	public abstract T newConnection(boolean setConnection, Object... o) throws Throwable;

	@SuppressWarnings("unchecked")
	public final <V> V getValue(Object o) {
		return null == o ? null : (V) o;
	}

	public final String[] paths(String path, String separator) {
		StringTokenizer str = new StringTokenizer(path, separator);
		String[] result = new String[str.countTokens()];
		int i = 0;
		while (str.hasMoreTokens())
			result[i++] = str.nextToken();
		return result;
	}
}
