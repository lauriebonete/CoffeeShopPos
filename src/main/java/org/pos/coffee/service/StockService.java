package org.pos.coffee.service;

import org.evey.service.BaseCrudService;
import org.pos.coffee.bean.PurchaseOrder;
import org.pos.coffee.bean.Stock;
import org.pos.coffee.bean.helper.StockHelper;

import java.util.List;

/**
 * Created by Laurie on 1/4/2016.
 */
public interface StockService extends BaseCrudService<Stock> {
    public List<StockHelper> getStockCount(String queryName);
    public List<StockHelper> findStockEntity(StockHelper stockHelper);
    public void createInventoryForReceivingPO(List<PurchaseOrder> purchaseOrderList);
}
