package org.pos.coffee.dao;

import org.evey.dao.BaseEntityDao;
import org.pos.coffee.bean.Stock;
import org.pos.coffee.bean.helper.StockHelper;

import java.util.List;

/**
 * Created by Laurie on 1/4/2016.
 */
public interface StockDao extends BaseEntityDao<Stock,Long> {

    public List<StockHelper> getStockCount(String queryName);
}
