package org.pos.coffee.dao.impl;

import org.evey.dao.impl.BaseEntityDaoJpaImpl;
import org.pos.coffee.bean.Sale;
import org.pos.coffee.dao.SaleDao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
 * Created by Laurie on 12/14/2015.
 */
@Repository("saleDao")
public class SaleDaoJpaImpl extends BaseEntityDaoJpaImpl<Sale,Long> implements SaleDao {

    @Override
    public void updateTotalCost(Long saleId, Double totalCost) {
        String update = "UPDATE Sale sale SET sale.totalCost = :totalCost where sale.id = :saleId";
        Query updateQuery = getEntityManager().createQuery(update);
        updateQuery.setParameter("totalCost",totalCost);
        updateQuery.setParameter("saleId",saleId);
        updateQuery.executeUpdate();
    }
}
