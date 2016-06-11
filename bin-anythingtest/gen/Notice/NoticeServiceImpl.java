package project.freehelp.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.freehelp.common.dao.NoticeDao;
import project.freehelp.common.entity.Notice;
import project.freehelp.common.service.NoticeService;
import project.master.dbmaamger.dao.BaseDao;
import project.master.dbmaamger.service.AbstractService;

@Component
public class NoticeServiceImpl extends AbstractService<Notice, String> implements NoticeService {

	@Autowired
	private NoticeDao noticeDao;

	@Override
	public BaseDao<Notice, String> getService() {
		return noticeDao;
	}

}
