package Controller.entity.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.db.manager.DBAdapter;
import com.cheuks.bin.db.manager.dao.AbstractDao;

import Controller.entity.RolePermission;

@Component
public class RolePermissionDao extends AbstractDao<RolePermission, String> {

	@Autowired
	private DBAdapter dBAdapter;

	@Override
	public Class<RolePermission> getEntityClass() {
		return RolePermission.class;
	}

	@Override
	public DBAdapter getDBAdapter() {
		return dBAdapter;
	}

}
