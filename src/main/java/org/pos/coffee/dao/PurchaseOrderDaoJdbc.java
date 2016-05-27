package org.pos.coffee.dao;

import org.pos.coffee.bean.helper.PendingPurchaseDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by Laurie on 5/22/2016.
 */
public interface PurchaseOrderDaoJdbc {

    public List<PendingPurchaseDTO> getPendingPurchases();
}
