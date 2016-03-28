package com.cheuks.bin.registercenter;

import java.util.List;

public interface Register {

	public String register(String node) throws Throwable;

	public boolean existi(String node) throws Throwable;

	public List<?> childList(String node) throws Throwable;

}
