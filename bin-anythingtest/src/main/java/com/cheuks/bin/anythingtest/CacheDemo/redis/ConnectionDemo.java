package com.cheuks.bin.anythingtest.CacheDemo.redis;

import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;

public class ConnectionDemo {

	static JedisPoolConfig config = new JedisPoolConfig();
	static List<JedisShardInfo> shardInfos = new ArrayList<JedisShardInfo>();

	static {
		config.setMaxIdle(10);// 空闲
		config.setMaxTotal(300);
		config.setMaxWaitMillis(3 * 1000);
		// 分布式连接池信息
		shardInfos.add(new JedisShardInfo("121.42.27.147", 6379));
	}

	//单连
	static JedisPool pool = new JedisPool(config, "121.42.27.147", 6379);
	// 分布式连接池
	static ShardedJedisPool shardedPool = new ShardedJedisPool(config, shardInfos);

	public static void main(String[] args) {
		System.err.println(Long.toString((2L << 30)));
		// Jedis client = new Jedis("192.168.168.148", 6379);
		//单连
		Jedis client = pool.getResource();
		client.set("hello", "你好吗？");
		System.out.println(client.get("hello"));
		client.close();
		pool.destroy();
		//分布式连接池
		ShardedJedis shardClient = shardedPool.getResource();
		shardClient.set("hihi", "hello this shardedJedis");
		System.out.println(client.get("hihi"));
		shardClient.close();
		shardedPool.destroy();
	}

}
