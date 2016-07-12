package Controller.entity.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.db.manager.DBAdapter;
import com.cheuks.bin.db.manager.dao.AbstractDao;

import Controller.entity.Dict;

@Component
public class DictDao extends AbstractDao<Dict, Integer> {

	@Autowired
	private DBAdapter dBAdapter;

	@Override
	public Class<Dict> getEntityClass() {
		return Dict.class;
	}

	@Override
	public DBAdapter getDBAdapter() {
		return dBAdapter;
	}

//	@Override
//	public Dict save(Dict o) throws Throwable {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("id", 100);
//		params.put("parentId", 100);
//		dBAdapter.executeUpdate("Controller.entity.Dict.test".toLowerCase(), params, true, false);
//		return o;
//	}

}
