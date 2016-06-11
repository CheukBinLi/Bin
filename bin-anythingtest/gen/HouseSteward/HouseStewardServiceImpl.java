package project.freehelp.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.freehelp.common.dao.HouseStewardDao;
import project.freehelp.common.entity.HouseSteward;
import project.freehelp.common.service.HouseStewardService;
import project.master.dbmaamger.dao.BaseDao;
import project.master.dbmaamger.service.AbstractService;

@Component
public class HouseStewardServiceImpl extends AbstractService<HouseSteward, String> implements HouseStewardService {

	@Autowired
	private HouseStewardDao houseStewardDao;

	@Override
	public BaseDao<HouseSteward, String> getService() {
		return houseStewardDao;
	}

}
