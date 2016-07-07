package Controller.shiro;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import Controller.entity.Permission;
import Controller.entity.Role;
import Controller.entity.RolePermission;
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

	@PostConstruct
	public void x() throws Throwable {
		List<Role> roles = roleService.getList(null, false, 0, 0);
		List<Permission> permissions = permissionService.getList(null, false, 0, 0);
		List<RolePermission> RolePermissions = rolePermissionService.getList(null, false, 0, 0);
		System.err.println();
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		System.out.println("1");
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		System.out.println("2");
		UsernamePasswordToken token = (UsernamePasswordToken) arg0;
		// User user = userService.getByAccount(token.getUsername());
		// if (user != null) {
		// return new SimpleAuthenticationInfo(user.getAccount(), user.getPassword(), user.getNickname());
		return new SimpleAuthenticationInfo("sa", "123", "小明");
		// } else {
		// return null;
		// }
	}

}
