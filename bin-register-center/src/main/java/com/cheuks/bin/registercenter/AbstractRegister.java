package com.cheuks.bin.registercenter;

public abstract class AbstractRegister<T> implements Register {

	public abstract T newConnection(boolean setConnection, Object... o) throws Throwable;

	@SuppressWarnings("unchecked")
	public final <V> V getValue(Object o) {
		return null == o ? null : (V) o;
	}

}
