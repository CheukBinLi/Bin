package project.freehelp.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.freehelp.common.dao.OrderDao;
import project.freehelp.common.entity.Order;
import project.freehelp.common.service.OrderService;
import project.master.dbmaamger.dao.BaseDao;
import project.master.dbmaamger.service.AbstractService;

@Component
public class OrderServiceImpl extends AbstractService<Order, String> implements OrderService {

	@Autowired
	private OrderDao orderDao;

	@Override
	public BaseDao<Order, String> getService() {
		return orderDao;
	}

}
