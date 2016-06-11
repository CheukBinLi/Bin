package project.freehelp.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.freehelp.common.dao.HouseInfoDao;
import project.freehelp.common.entity.HouseInfo;
import project.freehelp.common.service.HouseInfoService;
import project.master.dbmaamger.dao.BaseDao;
import project.master.dbmaamger.service.AbstractService;

@Component
public class HouseInfoServiceImpl extends AbstractService<HouseInfo, String> implements HouseInfoService {

	@Autowired
	private HouseInfoDao houseInfoDao;

	@Override
	public BaseDao<HouseInfo, String> getService() {
		return houseInfoDao;
	}

}
