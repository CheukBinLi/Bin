package project.freehelp.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.freehelp.common.dao.UserInfoDao;
import project.freehelp.common.entity.UserInfo;
import project.freehelp.common.service.UserInfoService;
import project.master.dbmaamger.dao.BaseDao;
import project.master.dbmaamger.service.AbstractService;

@Component
public class UserInfoServiceImpl extends AbstractService<UserInfo, String> implements UserInfoService {

	@Autowired
	private UserInfoDao userInfoDao;

	@Override
	public BaseDao<UserInfo, String> getService() {
		return userInfoDao;
	}

}
