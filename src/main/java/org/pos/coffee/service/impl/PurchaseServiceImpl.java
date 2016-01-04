package org.pos.coffee.service.impl;

import org.evey.service.impl.BaseCrudServiceImpl;
import org.pos.coffee.bean.Purchase;
import org.pos.coffee.service.PurchaseService;
import org.springframework.stereotype.Service;

/**
 * Created by Laurie on 1/4/2016.
 */
@Service("purchaseService")
public class PurchaseServiceImpl extends BaseCrudServiceImpl<Purchase> implements PurchaseService {
}
