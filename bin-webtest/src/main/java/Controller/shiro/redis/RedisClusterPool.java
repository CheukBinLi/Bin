package Controller.shiro.redis;

import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class RedisClusterPool extends JedisCluster {

	public RedisClusterPool(HostAndPort node, int timeout, int maxRedirections) {
		super(node, timeout, maxRedirections);
		// TODO Auto-generated constructor stub
	}

	public RedisClusterPool(HostAndPort node) {
		super(node);
	}

	public RedisClusterPool(HostAndPort node, GenericObjectPoolConfig poolConfig) {
		super(node, poolConfig);
	}

	public RedisClusterPool(Set<HostAndPort> jedisClusterNode, int connectionTimeout, int soTimeout, int maxRedirections, GenericObjectPoolConfig poolConfig) {
		super(jedisClusterNode, connectionTimeout, soTimeout, maxRedirections, poolConfig);
		// TODO Auto-generated constructor stub
	}

}
