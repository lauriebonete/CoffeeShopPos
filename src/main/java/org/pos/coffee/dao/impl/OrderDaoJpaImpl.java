package org.pos.coffee.dao.impl;

import org.evey.dao.impl.BaseEntityDaoJpaImpl;
import org.pos.coffee.bean.Order;
import org.pos.coffee.dao.OrderDao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
 * Created by Laurie on 12/8/2015.
 */
@Repository("orderDao")
public class OrderDaoJpaImpl extends BaseEntityDaoJpaImpl<Order,Long> implements OrderDao {

    @Override
    public void updateTotalExpense(Long orderId, Double totalExpense) {
        String update = "UPDATE Order o SET o.totalLineExpense = :totalExpense where o.id = :orderId";
        Query updateQuery = getEntityManager().createQuery(update);
        updateQuery.setParameter("totalExpense",totalExpense);
        updateQuery.setParameter("orderId",orderId);
        updateQuery.executeUpdate();
    }
}
