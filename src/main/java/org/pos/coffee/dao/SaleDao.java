package org.pos.coffee.dao;

import org.evey.dao.BaseEntityDao;
import org.pos.coffee.bean.Sale;

/**
 * Created by Laurie on 12/14/2015.
 */

public interface SaleDao extends BaseEntityDao<Sale, Long> {
    public void updateTotalCost(Long saleId, Double totalCost);
}
