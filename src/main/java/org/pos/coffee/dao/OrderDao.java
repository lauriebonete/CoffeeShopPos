package org.pos.coffee.dao;

import org.evey.dao.BaseEntityDao;
import org.pos.coffee.bean.Order;

/**
 * Created by Laurie on 12/8/2015.
 */
public interface OrderDao extends BaseEntityDao<Order,Long> {

    public void updateTotalExpense(Long orderId, Double totalExpense);
}
