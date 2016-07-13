package Controller.entity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.db.manager.dao.BaseDao;
import com.cheuks.bin.db.manager.service.AbstractService;

import Controller.entity.RolePermission;
import Controller.entity.dao.RolePermissionDao;

@Component
public class RolePermissionServiceImpl extends AbstractService<RolePermission, String> implements RolePermissionService {

	@Autowired
	private RolePermissionDao rolePermissionDao;

	@Override
	public BaseDao<RolePermission, String> getDao() {
		return rolePermissionDao;
	}

	public RolePermissionServiceImpl() {
		super();
	}

}
