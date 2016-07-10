package Controller.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import Controller.entity.Permission;
import Controller.entity.service.PermissionService;

public class CutomShiroFilterFactoryBean extends ShiroFilterFactoryBean {

	private volatile boolean isInit;

	@Autowired
	private PermissionService permissionService;

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
		map.put("/error", "anon");
		// map.put("/**", "authc");
		// map.put("/**", "roles[admin]");
		map.put("/**", "perms[11,12,13]");
		if (null != getFilterChainDefinitionMap())
			map.putAll(getFilterChainDefinitionMap());
		setFilterChainDefinitionMap(map);
	}
}
