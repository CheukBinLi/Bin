package Controller.shiro.cache;

public interface ShiroSerializable {

	byte[] encode(Object o) throws Throwable;

	Object decode(byte[] o) throws Throwable;

	<T> T decodeT(byte[] o) throws Throwable;

}
