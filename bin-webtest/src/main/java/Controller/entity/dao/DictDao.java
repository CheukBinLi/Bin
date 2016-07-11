package Controller.entity.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.db.manager.DBAdapter;
import com.cheuks.bin.db.manager.dao.AbstractDao;

import Controller.entity.Dict;

@Component
public class DictDao extends AbstractDao<Dict, Integer>  {

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

}
