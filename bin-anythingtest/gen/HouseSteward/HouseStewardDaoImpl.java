package project.freehelp.common.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.freehelp.common.dao.HouseStewardDao;
import project.freehelp.common.entity.HouseSteward;
import project.master.dbmaamger.DBAdapter;
import project.master.dbmaamger.dao.AbstractDao;

@Component
public class HouseStewardDaoImpl extends AbstractDao<HouseSteward, String> implements HouseStewardDao {

	@Autowired
	private DBAdapter dBAdapter;

	@Override
	public Class<HouseSteward> getEntityClass() {
		return HouseSteward.class;
	}

	@Override
	public DBAdapter getDBAdapter() {
		return dBAdapter;
	}

}
