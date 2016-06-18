package Controller.shiro.cache;

public interface Cache<K /* extends Serializable */, V /* extends Serializable */> {

	K getKey();

	V getValue();

}
