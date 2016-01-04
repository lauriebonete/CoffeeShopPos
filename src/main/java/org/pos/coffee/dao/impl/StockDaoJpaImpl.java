package org.pos.coffee.dao.impl;

import org.evey.dao.impl.BaseEntityDaoJpaImpl;
import org.pos.coffee.bean.Stock;
import org.pos.coffee.dao.StockDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Laurie on 1/4/2016.
 */
@Repository("stockDao")
public class StockDaoJpaImpl extends BaseEntityDaoJpaImpl<Stock,Long> implements StockDao {
}
