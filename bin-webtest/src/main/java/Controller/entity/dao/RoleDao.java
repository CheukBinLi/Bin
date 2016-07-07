package Controller.entity.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.db.manager.DBAdapter;
import com.cheuks.bin.db.manager.dao.AbstractDao;

import Controller.entity.Role;

@Component
public class RoleDao extends AbstractDao<Role, String> {

	@Autowired
	private DBAdapter dBAdapter;

	@Override
	public Class<Role> getEntityClass() {
		return Role.class;
	}

	@Override
	public DBAdapter getDBAdapter() {
		return dBAdapter;
	}

}
