package org.pos.coffee.service.impl;

import org.evey.service.impl.BaseCrudServiceImpl;
import org.pos.coffee.bean.PurchaseOrder;
import org.pos.coffee.service.ItemService;
import org.pos.coffee.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Laurie on 1/4/2016.
 */
@Service("purchaseOrderService")
public class PurchaseOrderServiceImpl extends BaseCrudServiceImpl<PurchaseOrder> implements PurchaseOrderService {

    @Autowired
    private ItemService itemService;

    @Override
    public Double countTotalExpense(List<PurchaseOrder> purchaseOrderList) {
        Double totalExpense = 0D;
        for(PurchaseOrder purchaseOrder: purchaseOrderList){
            totalExpense += purchaseOrder.getPrice();
        }
        return totalExpense;
    }

    @Override
    public void loadItem(List<PurchaseOrder> purchaseOrderList) {
        for(PurchaseOrder purchaseOrder: purchaseOrderList){
            purchaseOrder.setOrderedItem(itemService.load(purchaseOrder.getOrderedItem().getId()));
        }
    }
}
