package org.pos.coffee.dao.impl;

import org.evey.dao.impl.BaseEntityDaoJpaImpl;
import org.pos.coffee.bean.Sale;
import org.pos.coffee.dao.SaleDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Laurie on 12/14/2015.
 */
@Repository("saleDao")
public class SaleDaoJpaImpl extends BaseEntityDaoJpaImpl<Sale,Long> implements SaleDao {
}
