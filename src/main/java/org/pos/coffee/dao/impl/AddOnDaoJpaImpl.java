package org.pos.coffee.dao.impl;

import org.evey.dao.impl.BaseEntityDaoJpaImpl;
import org.pos.coffee.bean.AddOn;
import org.pos.coffee.dao.AddOnDao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
 * Created by Laurie on 1/20/2016.
 */
@Repository("addOnDao")
public class AddOnDaoJpaImpl extends BaseEntityDaoJpaImpl<AddOn,Long> implements AddOnDao {

    @Override
    public void updateAddOnCost(Long addOnId, Double cost) {
        String update = "UPDATE AddOn o SET o.cost = :cost where o.id = :addOnId";
        Query updateQuery = getEntityManager().createQuery(update);
        updateQuery.setParameter("cost",cost);
        updateQuery.setParameter("addOnId",addOnId);
        updateQuery.executeUpdate();
    }
}
