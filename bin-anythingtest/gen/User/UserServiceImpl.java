package Controller.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.freehelp.common.dao.UserDao;
import project.freehelp.common.entity.User;
import project.freehelp.common.service.UserService;
import project.master.dbmaamger.dao.BaseDao;
import project.master.dbmaamger.service.AbstractService;

@Component
public class UserServiceImpl extends AbstractService<User, int> implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public BaseDao<User, int> getService() {
		return userDao;
	}

}
