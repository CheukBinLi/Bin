package Controller.entity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.db.manager.dao.BaseDao;
import com.cheuks.bin.db.manager.service.AbstractService;

import Controller.entity.Role;
import Controller.entity.dao.RoleDao;

@Component
public class RoleServiceImpl extends AbstractService<Role, String> implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	public BaseDao<Role, String> getService() {
		return roleDao;
	}

	public RoleServiceImpl() {
		super();
	}

}
