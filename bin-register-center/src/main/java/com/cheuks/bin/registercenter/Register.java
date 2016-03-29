package com.cheuks.bin.registercenter;

import java.util.List;

public interface Register<CreateModel> {

	public String register(String node, byte[] value) throws Throwable;

	public String create(String node, byte[] value, CreateModel createModel) throws Throwable;

	public boolean exists(String node) throws Throwable;

	public List<?> childList(String node) throws Throwable;

}
