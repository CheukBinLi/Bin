package Controller.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class Realm extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		System.out.println("1");
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		System.out.println("2");
		UsernamePasswordToken token = (UsernamePasswordToken) arg0;
		//		User user = userService.getByAccount(token.getUsername());
		//		if (user != null) {
		//			return new SimpleAuthenticationInfo(user.getAccount(), user.getPassword(), user.getNickname());
		return new SimpleAuthenticationInfo("sa", "123", "小明");
		//		} else {
		//			return null;
		//		}
	}

}
