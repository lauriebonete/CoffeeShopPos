package org.pos.coffee.dao.impl;

import org.pos.coffee.bean.ListPrice;
import org.pos.coffee.dao.ListPriceDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Laurie on 12/1/2015.
 */
@Repository("listPriceDao")
public class ListPriceDaoJpaImpl extends BaseEntityDaoJpaImpl<ListPrice, Long> implements ListPriceDao {
}
