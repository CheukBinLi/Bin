package project.freehelp.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.freehelp.common.dao.DictionaryDao;
import project.freehelp.common.entity.Dictionary;
import project.freehelp.common.service.DictionaryService;
import project.master.dbmaamger.dao.BaseDao;
import project.master.dbmaamger.service.AbstractService;

@Component
public class DictionaryServiceImpl extends AbstractService<Dictionary, Integer> implements DictionaryService {

	@Autowired
	private DictionaryDao dictionaryDao;

	@Override
	public BaseDao<Dictionary, Integer> getService() {
		return dictionaryDao;
	}

}
