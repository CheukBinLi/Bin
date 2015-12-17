package com.cheuks.bin.anythingtest.zookeeper.paxos.net;

public interface Log {

	public Log log(String msg, boolean write, int level);

	public Logger info(Class<?> clazz, String msg);

	public Logger info(String className, String msg);

	public Logger info(Class<?> clazz, String msg);
}
