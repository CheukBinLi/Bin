package com.cheuks.bin.anythingtest.CacheDemo.memcache;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeoutException;

public class ConnectionDemo {

	public static void x(String ip, int port, String msg) throws IOException, TimeoutException, InterruptedException, MemcachedException {

		MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(String.format("%s:%d", ip, port)));
		MemcachedClient client = builder.build();
		X x = new X(1, "XXX");
		client.set("A", 0, x);
		x = client.get("A");
//		System.out.println(client.get("A"));
		System.out.println(x.getName());
	}

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException, MemcachedException {
		x("121.42.27.147", 11211, "hello");
	}

	static class X implements Serializable {
		private int id;
		private String name;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public X(int id, String name) {
			super();
			this.id = id;
			this.name = name;
		}

	}
}
