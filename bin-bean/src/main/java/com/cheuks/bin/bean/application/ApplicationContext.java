package com.cheuks.bin.bean.application;

public interface ApplicationContext {

	<T> T getBeans(String name) throws Throwable;

}
