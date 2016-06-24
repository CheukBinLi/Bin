package Controller.entity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cheuks.bin.db.manager.dao.BaseDao;
import com.cheuks.bin.db.manager.service.AbstractService;

import Controller.entity.LuceneRss;
import Controller.entity.dao.LuceneRssDao;

@Component
public class LuceneRssServiceImpl extends AbstractService<LuceneRss, String> implements LuceneRssService {

	@Autowired
	private LuceneRssDao luceneRssDao;

	@Override
	public BaseDao<LuceneRss, String> getService() {
		return luceneRssDao;
	}

	public LuceneRssServiceImpl() {
		super();
		System.err.println(this.getClass().getName());
	}

}
