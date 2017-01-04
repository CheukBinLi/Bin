package Controller.shiro.redis;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.Assert;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

public class RedisClusterManager extends AbstractRedisClusterBinaryManager<JedisCluster> {

	private static transient final Logger LOG = LoggerFactory.getLogger(RedisClusterManager.class);

	private int maxIdle = 50;// 空闲
	private int maxTotal = 300;
	private int maxWaitMillis = 5000;
	private int soTimeOut = 500;
	private int connectionTimeout = 30000;
	private int maxRedirections = 8;// 最大重定向
	// private String serverList;
	private String serverList = "192.1681.1.200:2000,192.168.1.201:2001,192.168.1.202:2002,192.168.1.203:2003,192.168.1.204:2004,192.168.1.205:2005";
	private boolean testOnBorrow;// ping
	private JedisPoolConfig config;
	protected static JedisCluster jedisCluster;
	protected Set<HostAndPort> hosts;
	private volatile boolean isInit;

	@PostConstruct
	void init() {
		if (isInit)
			return;
		isInit = true;
		if (LOG.isInfoEnabled())
			LOG.info("ClusterJedisManager:init");
		hosts = new HashSet<HostAndPort>();
		config = new JedisPoolConfig();
		config.setMaxIdle(this.maxIdle);
		config.setMaxTotal(this.maxTotal);
		config.setMaxWaitMillis(this.maxWaitMillis);
		config.setTestOnBorrow(testOnBorrow);
		if (null == this.serverList) {
			LOG.warn("servlet is null.");
			return;
		}
		StringTokenizer ip = new StringTokenizer(serverList, ",");
		String[] split;
		while (ip.hasMoreTokens()) {
			split = ip.nextToken().split(":");
			if (null == split || split.length < 2)
				continue;
			hosts.add(new HostAndPort(split[0], Integer.valueOf(split[1])));
		}

		jedisCluster = new JedisCluster(hosts, getConnectionTimeout(), soTimeOut, maxRedirections, config);
		if (null == getRedisSerialize())
			setRedisSerialize(new DefaultRedisSerialize());
		if (LOG.isInfoEnabled())
			LOG.info("ClusterJedisManager:complete");
	}

	public RedisClusterManager() {
		super();
	}

	@Override
	public JedisCluster getResource() {
		if (!isInit)
			synchronized (this) {
				init();
			}
		return jedisCluster;
	}

	@Override
	public void destory(JedisCluster jedis) {

	}

	@Override
	public Logger getLog() {
		return LOG;
	}

	public RedisClusterManager setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
		return this;
	}

	public RedisClusterManager setServerList(String serverList) {
		this.serverList = serverList;
		return this;
	}

	public RedisClusterManager setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
		return this;
	}

	public RedisClusterManager setMaxRedirections(int maxRedirections) {
		this.maxRedirections = maxRedirections;
		return this;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public void setMaxWaitMillis(int maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

	public JedisPoolConfig getConfig() {
		return config;
	}

	public void setConfig(JedisPoolConfig config) {
		this.config = config;
	}

	public int getMaxTotal() {
		return maxTotal;
	}

	public int getMaxRedirections() {
		return maxRedirections;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public Set<HostAndPort> getHosts() {
		return hosts;
	}

	public int getSoTimeOut() {
		return soTimeOut;
	}

	public RedisClusterManager setSoTimeOut(int soTimeOut) {
		this.soTimeOut = soTimeOut;
		return this;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public RedisClusterManager setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
		return this;
	}

	public static void main(String[] args) throws RedisExcecption {
		RedisManager rm = new RedisClusterManager();
		rm.set("AA".getBytes(), "xx".getBytes());
		rm.setObject("GOOD", 1111);
		rm.set("GOOD1", "1111");
		Assert.assertNull("YES", null);

	}
}
