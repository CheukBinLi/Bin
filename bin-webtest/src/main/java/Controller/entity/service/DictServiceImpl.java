package Controller.entity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.db.manager.dao.BaseDao;
import com.cheuks.bin.db.manager.service.AbstractService;

import Controller.entity.Dict;
import Controller.entity.dao.DictDao;

@Component
public class DictServiceImpl extends AbstractService<Dict, Integer> implements DictService {

	@Autowired
	private DictDao dictDao;

	@Override
	public BaseDao<Dict, Integer> getService() {
		return dictDao;
	}

}
