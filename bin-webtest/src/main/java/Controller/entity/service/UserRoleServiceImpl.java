package Controller.entity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.db.manager.dao.BaseDao;
import com.cheuks.bin.db.manager.service.AbstractService;

import Controller.entity.UserRole;
import Controller.entity.dao.UserRoleDao;

@Component
public class UserRoleServiceImpl extends AbstractService<UserRole, String> implements UserRoleService {

	@Autowired
	private UserRoleDao userRoleDao;

	@Override
	public BaseDao<UserRole, String> getService() {
		return userRoleDao;
	}

	public UserRoleServiceImpl() {
		super();
	}

}
