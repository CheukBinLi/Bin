package Controller.shiro;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.cheuks.bin.db.manager.Scan;

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
		List<Permission> permissions = null;
		try {
			permissions = permissionService.getList(null, false, 0, 0);
			isInit = true;
		} catch (Throwable e) {
			e.printStackTrace();
		}

		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("/login", "anon");
		map.put("/resource/**", "anon");
		map.put("/back", "anon");
		map.put("/verificationcode", "anon");
		map.put("/verification", "anon");
		map.put("/error", "anon");
		map.put("/**", "authc");
		// map.put("/**", "roles[admin]");
		// map.put("/**", "perms[11,12,13]");
		// map.put("/**", "perms[adnmi:add]");
		if (null != getFilterChainDefinitionMap())
			map.putAll(getFilterChainDefinitionMap());
		if (null != permissions)
			for (Permission p : permissions)
				map.put(p.getUrl(), "perms[" + p.getId() + "]");
		// setFilterChainDefinitionMap(map);
		getFilterChainDefinitionMap().putAll(map);
	}

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		Object o = Scan.doScan("/**/*.query.xml,/**/*.query2.xml");
		System.out.println(o);
	}
}
