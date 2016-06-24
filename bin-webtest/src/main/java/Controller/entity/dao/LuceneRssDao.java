package Controller.entity.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.db.manager.DBAdapter;
import com.cheuks.bin.db.manager.dao.AbstractDao;

import Controller.entity.LuceneRss;

@Component
public class LuceneRssDao extends AbstractDao<LuceneRss, String> {

	@Autowired
	private DBAdapter dBAdapter;

	@Override
	public Class<LuceneRss> getEntityClass() {
		return LuceneRss.class;
	}

	@Override
	public DBAdapter getDBAdapter() {
		return dBAdapter;
	}

}
