package Controller.entity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.db.manager.dao.BaseDao;
import com.cheuks.bin.db.manager.service.AbstractService;

import Controller.entity.User;
import Controller.entity.dao.UserDao;

@Component
public class UserServiceImpl extends AbstractService<User, String> implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public BaseDao<User, String> getDao() {
		return userDao;
	}

	public UserServiceImpl() {
		super();
	}

}
