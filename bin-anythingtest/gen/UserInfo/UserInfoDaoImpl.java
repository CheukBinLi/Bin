package project.freehelp.common.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.freehelp.common.dao.UserInfoDao;
import project.freehelp.common.entity.UserInfo;
import project.master.dbmaamger.DBAdapter;
import project.master.dbmaamger.dao.AbstractDao;

@Component
public class UserInfoDaoImpl extends AbstractDao<UserInfo, String> implements UserInfoDao {

	@Autowired
	private DBAdapter dBAdapter;

	@Override
	public Class<UserInfo> getEntityClass() {
		return UserInfo.class;
	}

	@Override
	public DBAdapter getDBAdapter() {
		return dBAdapter;
	}

}
