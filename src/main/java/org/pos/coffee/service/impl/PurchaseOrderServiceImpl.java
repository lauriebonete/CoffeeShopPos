package org.pos.coffee.service.impl;

import org.evey.service.impl.BaseCrudServiceImpl;
import org.pos.coffee.bean.PurchaseOrder;
import org.pos.coffee.service.PurchaseOrderService;
import org.springframework.stereotype.Service;

/**
 * Created by Laurie on 1/4/2016.
 */
@Service("purchaseOrderService")
public class PurchaseOrderServiceImpl extends BaseCrudServiceImpl<PurchaseOrder> implements PurchaseOrderService {
}
