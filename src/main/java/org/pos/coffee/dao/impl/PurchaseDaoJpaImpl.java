package org.pos.coffee.dao.impl;

import org.evey.dao.impl.BaseEntityDaoJpaImpl;
import org.pos.coffee.bean.Purchase;
import org.pos.coffee.dao.PurchaseDao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by Laurie on 1/4/2016.
 */
@Repository("purchaseDao")
public class PurchaseDaoJpaImpl extends BaseEntityDaoJpaImpl<Purchase, Long> implements PurchaseDao {

    @Override
    public List<Purchase> findAll() {
        Query query = getEntityManager().createQuery("select obj from "+ getEntityBeanType().getName()+" obj ORDER BY obj.id DESC ");
        return query.getResultList();
    }
}
