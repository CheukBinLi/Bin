package project.freehelp.common.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.freehelp.common.dao.NoticeDao;
import project.freehelp.common.entity.Notice;
import project.master.dbmaamger.DBAdapter;
import project.master.dbmaamger.dao.AbstractDao;

@Component
public class NoticeDaoImpl extends AbstractDao<Notice, String> implements NoticeDao {

	@Autowired
	private DBAdapter dBAdapter;

	@Override
	public Class<Notice> getEntityClass() {
		return Notice.class;
	}

	@Override
	public DBAdapter getDBAdapter() {
		return dBAdapter;
	}

}
