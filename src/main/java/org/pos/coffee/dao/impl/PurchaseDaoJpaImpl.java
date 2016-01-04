package org.pos.coffee.dao.impl;

import org.evey.dao.impl.BaseEntityDaoJpaImpl;
import org.pos.coffee.bean.Purchase;
import org.pos.coffee.dao.PurchaseDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Laurie on 1/4/2016.
 */
@Repository("purchaseDao")
public class PurchaseDaoJpaImpl extends BaseEntityDaoJpaImpl<Purchase, Long> implements PurchaseDao {
}
