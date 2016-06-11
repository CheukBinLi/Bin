package project.master.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.freehelp.common.dao.UserDao;
import project.freehelp.common.entity.User;
import project.master.dbmaamger.DBAdapter;
import project.master.dbmaamger.dao.AbstractDao;

@Component
public class UserDaoImpl extends AbstractDao<User, String> implements UserDao {

	@Autowired
	private DBAdapter dBAdapter;

	@Override
	public Class<User> getEntityClass() {
		return User.class;
	}

	@Override
	public DBAdapter getDBAdapter() {
		return dBAdapter;
	}

}
