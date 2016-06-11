package project.freehelp.common.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.freehelp.common.dao.DictionaryDao;
import project.freehelp.common.entity.Dictionary;
import project.master.dbmaamger.DBAdapter;
import project.master.dbmaamger.dao.AbstractDao;

@Component
public class DictionaryDaoImpl extends AbstractDao<Dictionary, Integer> implements DictionaryDao {

	@Autowired
	private DBAdapter dBAdapter;

	@Override
	public Class<Dictionary> getEntityClass() {
		return Dictionary.class;
	}

	@Override
	public DBAdapter getDBAdapter() {
		return dBAdapter;
	}

}
