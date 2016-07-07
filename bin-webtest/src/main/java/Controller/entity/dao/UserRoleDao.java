package Controller.entity.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.db.manager.DBAdapter;
import com.cheuks.bin.db.manager.dao.AbstractDao;

import Controller.entity.UserRole;

@Component
public class UserRoleDao extends AbstractDao<UserRole, String> {

	@Autowired
	private DBAdapter dBAdapter;

	@Override
	public Class<UserRole> getEntityClass() {
		return UserRole.class;
	}

	@Override
	public DBAdapter getDBAdapter() {
		return dBAdapter;
	}

}
