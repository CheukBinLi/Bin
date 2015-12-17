package com.cheuks.bin.anythingtest.zookeeper.paxos.net;

public interface Log {

	public Log log(String msg, boolean write, int level);

	public Log info(Class<?> clazz, String msg);

	public Log info(String className, String msg);

	public Log info(Class<?> clazz, Throwable msg);

	public Log error(Class<?> clazz, String msg);

	public Log error(String className, String msg);

	public Log error(Class<?> clazz, Throwable msg);

	public Log print(String className, String msg);
}
