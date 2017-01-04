package Controller.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Controller.shiro.redis.AbstractJedisManager;
import Controller.shiro.redis.DefaultRedisSerialize;
import Controller.shiro.redis.RedisExcecption;
import Controller.shiro.redis.RedisManager;
import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class ClusterTest extends AbstractJedisManager<JedisCluster> {

	protected Set<HostAndPort> hosts = new HashSet<HostAndPort>();
	protected JedisCluster jedisCluster;

	public ClusterTest() {
		super();
		hosts.add(new HostAndPort("192.168.1.200", 2000));
		hosts.add(new HostAndPort("192.168.1.201", 2001));
		hosts.add(new HostAndPort("192.168.1.202", 2002));
		hosts.add(new HostAndPort("192.168.1.203", 2003));
		hosts.add(new HostAndPort("192.168.1.204", 2004));
		hosts.add(new HostAndPort("192.168.1.205", 2005));
		jedisCluster = new JedisCluster(hosts, 2000, 10);
		setRedisSerialize(new DefaultRedisSerialize());
	}

	public JedisCluster getResource() {
		return jedisCluster;
	}

	public void destory(JedisCluster jedis) {
	}

	public void x() {
		getResource().del("setAAA");
		getResource().hset("setAAA", "A", "A_VALUE");
		getResource().hset("setAAA", "B", "B_VALUE");
		getResource().expire("setAAA", 20);
		// System.out.println(getResource().append("setAAA", "C"));
		getResource().lpush("list", "1");
		getResource().lpush("list", "2");
		getResource().lpush("list", "3");
		getResource().lpush("list", "4");
		getResource().rpush("list", "5");
		getResource().expire("list", 20);
		// getResource().incrby
		// Map a = getResource().hgetAll("setAAA");
		// System.err.println(a);
		// System.err.println(getResource().lrange("list", 0, -1));
		// System.err.println(getResource().rpop("list"));
	}

	public static void main(String[] args) throws RedisExcecption {

		ClusterTest rm = new ClusterTest();

		rm.set("AAAAAAAAAAAA", 123, 360);
		System.err.println(rm.getObject("AAAAAAAAAAAA"));
		rm.x();
	}

	public void delete(Serializable key) throws RedisExcecption {
		// TODO Auto-generated method stub

	}

	public boolean set(Serializable key, Serializable value) throws RedisExcecption {
		return false;
	}

	public boolean set(Serializable key, Serializable value, int expireSeconds) throws RedisExcecption {
		try {
			System.err.print(getResource().setex(key.toString().getBytes(), expireSeconds, getRedisSerialize().encode(value)));
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			// destory(jedisCluster);
		}
		return false;
	}

	public <T> Collection<T> getcollection() throws RedisExcecption {
		// TODO Auto-generated method stub
		return null;
	}

	public Serializable getAndSet(Serializable key, Serializable value) throws RedisExcecption {
		// TODO Auto-generated method stub
		return null;
	}

	public void expire(Serializable key, int expireSeconds) throws RedisExcecption {
		// TODO Auto-generated method stub

	}

	public <R> R get(Serializable key) throws RedisExcecption {
		try {
			return (R) getRedisSerialize().decode(getResource().get(getRedisSerialize().encode(key)));
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			// destory(jedisCluster);
		}
	}

	public boolean setMap(Serializable key, Serializable mapKey, Serializable value) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean setMap(Serializable key, Map<Serializable, Serializable> map) {
		// TODO Auto-generated method stub
		return false;
	}

	public <R> R getMap(Serializable key, Serializable field) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<Serializable, Serializable> getMap(Serializable key) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean mapRemove(Serializable key, Serializable field) {
		// TODO Auto-generated method stub
		return false;
	}

	public void delete(byte[] key) throws RedisExcecption {
		// TODO Auto-generated method stub

	}

	public boolean exists(byte[] key) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean set(byte[] key, byte[] value) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean incr(byte[] key, Integer value) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean set(byte[] key, byte[] value, int expireSeconds) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public byte[] getAndSet(byte[] key, byte[] value) throws RedisExcecption {
		// TODO Auto-generated method stub
		return null;
	}

	public byte[] get(byte[] key) throws RedisExcecption {
		// TODO Auto-generated method stub
		return null;
	}

	public List<byte[]> get(byte[]... key) throws RedisExcecption {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean setMap(byte[] key, byte[] mapKey, byte[] value) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean setMap(byte[] key, Map<byte[], byte[]> map) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean mapKeyExists(byte[] key, byte[] mapKey) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean incrByMap(byte[] key, byte[] mapKey, Integer value) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public byte[] getMapValue(byte[] key, byte[] mapKey) throws RedisExcecption {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean mapRemove(byte[] key, byte[]... mapKey) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public List<byte[]> getListByBytes(byte[] key, int start, int end) throws RedisExcecption {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean addListFirst(byte[] key, byte[]... value) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean addListLast(byte[] key, byte[]... value) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean removeListWithoutFor(byte[] key, int start, int end) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean setListIndex(byte[] key, int index, byte[] value) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public byte[] getListIndex(byte[] key, int index) throws RedisExcecption {
		// TODO Auto-generated method stub
		return null;
	}

	public long listLen(byte[] key) throws RedisExcecption {
		// TODO Auto-generated method stub
		return 0;
	}

	public byte[] popListFirst(byte[] key) throws RedisExcecption {
		// TODO Auto-generated method stub
		return null;
	}

	public byte[] popListLast(byte[] key) throws RedisExcecption {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(String key) throws RedisExcecption {
		// TODO Auto-generated method stub

	}

	public boolean exists(String key) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean set(String key, String value) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean incr(String key, Integer value) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean set(String key, String value, int expireSeconds) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public String getAndSet(String key, String value) throws RedisExcecption {
		// TODO Auto-generated method stub
		return null;
	}

	public String get(String key) throws RedisExcecption {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> get(String... key) throws RedisExcecption {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean setMap(String key, String mapKey, String value) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean setMap(String key, Map<String, String> map) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean mapKeyExists(String key, String mapKey) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean incrByMap(String key, String mapKey, Integer value) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public String getMapValue(String key, String mapKey) throws RedisExcecption {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean mapRemove(String key, String... mapKey) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public List<String> getListByString(String key, int start, int end) throws RedisExcecption {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean addListFirst(String key, String... value) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean addListLast(String key, String... value) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean removeListWithoutFor(String key, int start, int end) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean setListIndex(String key, int index, String value) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public String getListIndex(String key, int index) throws RedisExcecption {
		// TODO Auto-generated method stub
		return null;
	}

	public long listLen(String key) throws RedisExcecption {
		// TODO Auto-generated method stub
		return 0;
	}

	public String popListFirst(String key) throws RedisExcecption {
		// TODO Auto-generated method stub
		return null;
	}

	public String popListLast(String key) throws RedisExcecption {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean setObject(String key, Object value) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean setObject(String key, Object value, int expireSeconds) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public <R> R getAndSetObject(String key, Object value) throws RedisExcecption {
		// TODO Auto-generated method stub
		return null;
	}

	public <R> R getObject(String key) throws RedisExcecption {
		try {
			return (R) getResource().get(key.getBytes());
		} catch (Throwable e) {
			throw new RedisExcecption(e);
		} finally {
			// destory(jedisCluster);
		}
	}

	public boolean setMapObject(String key, Object mapKey, Object value) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean mapKeyExistsObject(String key, Object mapKey) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public Map<byte[], byte[]> getMapObject(String key) throws RedisExcecption {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean addListFirstObject(String key, Object value) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean addListLastObject(String key, Object value) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public <R> R getListIndexObject(String key, int index) throws RedisExcecption {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean setListIndexObject(String key, int index, Object value) throws RedisExcecption {
		// TODO Auto-generated method stub
		return false;
	}

	public long listLenObject(String key) throws RedisExcecption {
		// TODO Auto-generated method stub
		return 0;
	}

	public <R> R popListFirstObject(String key) throws RedisExcecption {
		// TODO Auto-generated method stub
		return null;
	}

	public <R> R popListLastObject(String key) throws RedisExcecption {
		// TODO Auto-generated method stub
		return null;
	}

}
