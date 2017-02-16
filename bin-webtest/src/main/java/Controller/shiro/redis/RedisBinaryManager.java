package Controller.shiro.redis;

import java.util.List;
import java.util.Map;

public interface RedisBinaryManager {

	public void delete(byte[] key) throws RedisExcecption;

	public boolean exists(byte[] key) throws RedisExcecption;

	public boolean set(byte[] key, byte[] value) throws RedisExcecption;

	public boolean set(byte[] key, byte[] value, int expireSeconds) throws RedisExcecption;

	/***
	 * 运算操作: +value
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @throws RedisExcecption
	 */
	public boolean incr(byte[] key, Integer value) throws RedisExcecption;

	/***
	 * 运算操作: +value
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @throws RedisExcecption
	 */
	public boolean incrByMap(byte[] key, byte[] mapKey, Integer value) throws RedisExcecption;

	public byte[] getAndSet(byte[] key, byte[] value) throws RedisExcecption;

	public byte[] get(byte[] key) throws RedisExcecption;

	public List<byte[]> get(byte[]... key) throws RedisExcecption;

	/**
	 * 设置数据有效时间 /***
	 * 
	 * Set<Map<mapKey,value>>
	 * 
	 * @param key
	 * @param mapKey
	 * @param value
	 * @return
	 */
	public boolean setMap(byte[] key, byte[] mapKey, byte[] value) throws RedisExcecption;

	public boolean setMap(byte[] key, Map<byte[], byte[]> map) throws RedisExcecption;

	public boolean mapKeyExists(byte[] key, byte[] mapKey) throws RedisExcecption;

	public byte[] getMapValue(byte[] key, byte[] mapKey) throws RedisExcecption;

	public boolean mapRemove(byte[] key, byte[]... mapKey) throws RedisExcecption;

	public List<byte[]> getListByBytes(byte[] key, int start, int end) throws RedisExcecption;

	/** List */

	public boolean addListFirst(byte[] key, byte[]... value) throws RedisExcecption;

	public boolean addListLast(byte[] key, byte[]... value) throws RedisExcecption;

	/***
	 * 删除列表，指定行除外
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public boolean removeListWithoutFor(byte[] key, int start, int end) throws RedisExcecption;

	/***
	 * 修改指定行
	 * 
	 * @param key
	 * @param index
	 * @param value
	 * @return
	 */
	public boolean setListIndex(byte[] key, int index, byte[] value) throws RedisExcecption;

	public byte[] getListIndex(byte[] key, int index) throws RedisExcecption;

	/***
	 * 获取列表长度
	 * 
	 * @param key
	 * @return
	 */
	public long listLen(byte[] key) throws RedisExcecption;

	public byte[] popListFirst(byte[] key) throws RedisExcecption;

	public byte[] popListLast(byte[] key) throws RedisExcecption;
}