package Controller.shiro;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import Controller.entity.Permission;
import Controller.entity.Role;
import Controller.entity.RolePermission;
import Controller.entity.User;
import Controller.entity.UserRole;
import Controller.entity.service.PermissionService;
import Controller.entity.service.RolePermissionService;
import Controller.entity.service.RoleService;
import Controller.entity.service.UserRoleService;
import Controller.entity.service.UserService;

public class Realm extends AuthorizingRealm {

	@Autowired
	private UserService userService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private RolePermissionService rolePermissionService;

	static final ConcurrentHashMap<String, User> userCache = new ConcurrentHashMap<String, User>();
	static final ConcurrentHashMap<Integer, Role> roleCache = new ConcurrentHashMap<Integer, Role>();
	static final ConcurrentHashMap<Integer, Permission> permissionCache = new ConcurrentHashMap<Integer, Permission>();
	static final ConcurrentHashMap<Integer, RolePermission> rolePermissionCache = new ConcurrentHashMap<Integer, RolePermission>();
	static final ConcurrentHashMap<Integer, UserRole> userRoleCache = new ConcurrentHashMap<Integer, UserRole>();

	static volatile boolean isInit;

	@PostConstruct
	public void initParam() throws Throwable {
		if (isInit)
			return;
		isInit = true;

		List<User> users = userService.getList(null, false, 0, 0);
		List<Role> roles = roleService.getList(null, false, 0, 0);
		List<Permission> permissions = permissionService.getList(null, false, 0, 0);
		List<RolePermission> RolePermissions = rolePermissionService.getList(null, false, 0, 0);
		List<UserRole> userRoles = userRoleService.getList(null, false, 0, 0);
		for (User u : users)
			userCache.put(u.getLogin() + u.getPassword(), u);
		for (Role r : roles)
			roleCache.put(r.getId(), r);
		for (Permission p : permissions)
			permissionCache.put(p.getId(), p);
		for (RolePermission rp : RolePermissions)
			rolePermissionCache.put(rp.getId(), rp);
		for (UserRole ur : userRoles)
			userRoleCache.put(ur.getId(), ur);

	}

	/***
	 * 获得授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		System.out.println("1#############################");
		System.err.println(arg0.getPrimaryPrincipal());
		System.out.println("1#############################");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(new HashSet<String>(Arrays.asList("admin")));
		info.setStringPermissions(new HashSet<String>(Arrays.asList("11", "12")));
		// info.setObjectPermissions(objectPermissions);
		return info;
	}

	/***
	 * 获得认证的信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken aToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) aToken;
		token.setRememberMe(true);
		User user = userCache.get(token.getUsername() + new String(token.getPassword()));
		// User user = userService.getByAccount(token.getUsername());
		if (user != null) {
			// if (Boolean.TRUE.equals(user.getLocked())) {
			// throw new LockedAccountException(); // 帐号锁定
			// }
			// return new SimpleAuthenticationInfo(user.getAccount(),
			// user.getPassword(), user.getNickname());
			return new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), user.getLogin());
			// } else {
			// return null;
			// }
		}
		throw new UnknownAccountException();// 没找到帐号
		// throw new AuthenticationException();
	}
}
