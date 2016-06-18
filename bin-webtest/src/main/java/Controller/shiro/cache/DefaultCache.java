package Controller.shiro.cache;

public class DefaultCache implements Cache<String, Object> {

	private String key;
	private Object value;

	public String getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}

	public DefaultCache() {
		super();
	}

	public DefaultCache(String key, Object value) {
		super();
		this.key = key;
		this.value = value;
	}

}
