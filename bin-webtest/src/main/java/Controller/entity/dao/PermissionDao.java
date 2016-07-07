package Controller.entity.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.db.manager.DBAdapter;
import com.cheuks.bin.db.manager.dao.AbstractDao;

import Controller.entity.Permission;

@Component
public class PermissionDao extends AbstractDao<Permission, String> {

	@Autowired
	private DBAdapter dBAdapter;

	@Override
	public Class<Permission> getEntityClass() {
		return Permission.class;
	}

	@Override
	public DBAdapter getDBAdapter() {
		return dBAdapter;
	}

}
