package X;

public interface ShiroSerializable {

	byte[] encode(Object o);

	Object decode(byte[] o);

	<T> T decodeT(byte[] o);

}
