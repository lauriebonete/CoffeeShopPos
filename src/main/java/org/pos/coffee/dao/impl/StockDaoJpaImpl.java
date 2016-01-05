package org.pos.coffee.dao.impl;

import org.evey.dao.impl.BaseEntityDaoJpaImpl;
import org.pos.coffee.bean.Stock;
import org.pos.coffee.bean.helper.StockHelper;
import org.pos.coffee.dao.StockDao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by Laurie on 1/4/2016.
 */
@Repository("stockDao")
public class StockDaoJpaImpl extends BaseEntityDaoJpaImpl<Stock,Long> implements StockDao {

    @Override
    public List<StockHelper> getStockCount(String queryName) {
        String queryString = getNamedQuery(queryName);
        Query query = getEntityManager().createQuery(queryString);
        List<StockHelper> results = query.getResultList();
        return results;
    }
}
