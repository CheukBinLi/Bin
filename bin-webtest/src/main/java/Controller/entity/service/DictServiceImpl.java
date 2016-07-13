package Controller.entity.service;

import java.util.HashMap;
import java.util.Map;

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
	public BaseDao<Dict, Integer> getDao() {
		return dictDao;
	}

	@Override
	public Dict saveCustom(Dict obj) throws Throwable {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", obj.getId());
		// Map<String,Object> map={"a":1};
		if (obj.getId() > 0 && getDao().getCount(params) > 0) {
			dictDao.update(obj);
			return obj;
		}
		return dictDao.saveCustom(obj);
	}

}
