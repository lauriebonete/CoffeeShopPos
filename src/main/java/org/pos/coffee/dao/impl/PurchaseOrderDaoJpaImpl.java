package org.pos.coffee.dao.impl;

import org.evey.dao.impl.BaseEntityDaoJpaImpl;
import org.pos.coffee.bean.PurchaseOrder;
import org.pos.coffee.dao.PurchaseOrderDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Laurie on 1/4/2016.
 */
@Repository("purchaseOrderDao")
public class PurchaseOrderDaoJpaImpl extends BaseEntityDaoJpaImpl<PurchaseOrder,Long> implements PurchaseOrderDao {
}
