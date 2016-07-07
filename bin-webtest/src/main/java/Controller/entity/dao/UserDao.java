package Controller.entity.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.db.manager.DBAdapter;
import com.cheuks.bin.db.manager.dao.AbstractDao;

import Controller.entity.User;

@Component
public class UserDao extends AbstractDao<User, String> {

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
