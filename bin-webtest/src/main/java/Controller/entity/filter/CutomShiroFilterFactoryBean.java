package Controller.entity.filter;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;

public class CutomShiroFilterFactoryBean extends ShiroFilterFactoryBean {

	private volatile boolean isInit;

	@Override
	public void setFilterChainDefinitionMap(Map<String, String> filterChainDefinitionMap) {
		super.setFilterChainDefinitionMap(filterChainDefinitionMap);
	}

	public CutomShiroFilterFactoryBean() {
		super();
		init();
		System.out.println("CutomShiroFilterFactoryBean");
	}

	@PostConstruct
	private void init() {
		if (this.isInit)
			return;
		isInit = true;
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("/login", "anon");
		map.put("/**", "authc");
		if (null != getFilterChainDefinitionMap())
			map.putAll(getFilterChainDefinitionMap());
		setFilterChainDefinitionMap(map);
	}

}
