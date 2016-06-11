package project.freehelp.common.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.freehelp.common.dao.HouseInfoDao;
import project.freehelp.common.entity.HouseInfo;
import project.master.dbmaamger.DBAdapter;
import project.master.dbmaamger.dao.AbstractDao;

@Component
public class HouseInfoDaoImpl extends AbstractDao<HouseInfo, String> implements HouseInfoDao {

	@Autowired
	private DBAdapter dBAdapter;

	@Override
	public Class<HouseInfo> getEntityClass() {
		return HouseInfo.class;
	}

	@Override
	public DBAdapter getDBAdapter() {
		return dBAdapter;
	}

}
