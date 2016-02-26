package org.pos.coffee.service;

import org.evey.service.BaseCrudService;
import org.pos.coffee.bean.PurchaseOrder;

import java.util.List;
import java.util.Map;

/**
 * Created by Laurie on 1/4/2016.
 */
public interface PurchaseOrderService extends BaseCrudService<PurchaseOrder> {
    public Double countTotalExpense(List<PurchaseOrder> purchaseOrderList);
    public void loadItem(List<PurchaseOrder> purchaseOrderList);
    public void receivePurchaseOrders(List<PurchaseOrder> target, List<PurchaseOrder> update);
    public Double recountTotalExpense(List<PurchaseOrder> purchaseOrderList);
    public Map getPendingPurchases();
}
