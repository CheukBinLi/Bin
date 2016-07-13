package Controller.entity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.db.manager.dao.BaseDao;
import com.cheuks.bin.db.manager.service.AbstractService;

import Controller.entity.Permission;
import Controller.entity.dao.PermissionDao;

@Component
public class PermissionServiceImpl extends AbstractService<Permission, String> implements PermissionService {

	@Autowired
	private PermissionDao permissionDao;

	@Override
	public BaseDao<Permission, String> getDao() {
		return permissionDao;
	}

	public PermissionServiceImpl() {
		super();
	}

}
