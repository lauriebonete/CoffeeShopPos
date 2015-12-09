package org.pos.coffee.dao.impl;

import org.evey.dao.impl.BaseEntityDaoJpaImpl;
import org.pos.coffee.bean.Order;
import org.pos.coffee.dao.OrderDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Laurie on 12/8/2015.
 */
@Repository("orderDao")
public class OrderDaoJpaImpl extends BaseEntityDaoJpaImpl<Order,Long> implements OrderDao {
}
