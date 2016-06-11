package project.freehelp.common.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.freehelp.common.dao.OrderDao;
import project.freehelp.common.entity.Order;
import project.master.dbmaamger.DBAdapter;
import project.master.dbmaamger.dao.AbstractDao;

@Component
public class OrderDaoImpl extends AbstractDao<Order, String> implements OrderDao {

	@Autowired
	private DBAdapter dBAdapter;

	@Override
	public Class<Order> getEntityClass() {
		return Order.class;
	}

	@Override
	public DBAdapter getDBAdapter() {
		return dBAdapter;
	}

}
