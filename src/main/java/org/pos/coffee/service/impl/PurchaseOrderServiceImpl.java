package org.pos.coffee.service.impl;

import org.evey.service.impl.BaseCrudServiceImpl;
import org.pos.coffee.bean.Item;
import org.pos.coffee.bean.PurchaseOrder;
import org.pos.coffee.dao.PurchaseDao;
import org.pos.coffee.dao.PurchaseOrderDao;
import org.pos.coffee.service.ItemService;
import org.pos.coffee.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Laurie on 1/4/2016.
 */
@Service("purchaseOrderService")
public class PurchaseOrderServiceImpl extends BaseCrudServiceImpl<PurchaseOrder> implements PurchaseOrderService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private PurchaseOrderDao purchaseOrderDao;

    @Override
    public Double countTotalExpense(List<PurchaseOrder> purchaseOrderList) {
        Double totalExpense = 0D;
        for(PurchaseOrder purchaseOrder: purchaseOrderList){
            Item item = itemService.load(purchaseOrder.getOrderedItem().getId());
            totalExpense += purchaseOrder.getOrderedQuantity()*item.getUnitPrice();
        }
        return totalExpense;
    }

    @Override
    public void loadItem(List<PurchaseOrder> purchaseOrderList) {
        for(PurchaseOrder purchaseOrder: purchaseOrderList){
            purchaseOrder.setOrderedItem(itemService.load(purchaseOrder.getOrderedItem().getId()));
        }
    }

    @Override
    @Transactional
    public void receivePurchaseOrders(List<PurchaseOrder> target, List<PurchaseOrder> update) {
        for(PurchaseOrder purchaseOrder: target){
            for(PurchaseOrder updateSource: update){
                if(updateSource.getReceivedQuantity()!=null){
                    if(purchaseOrder.equals(updateSource)){

                        Item item = itemService.load(purchaseOrder.getOrderedItem().getId());

                        purchaseOrder.setReceivedQuantity(updateSource.getReceivedQuantity());
                        purchaseOrder.setPrice(item.getUnitPrice()*purchaseOrder.getReceivedQuantity());
                        purchaseOrder.setIsReceived(true);
                        this.save(purchaseOrder);
                        break;
                    }
                } else {
                    break;
                }
            }
        }
    }

    @Override
    public Double recountTotalExpense(List<PurchaseOrder> purchaseOrderList) {
        Double totalExpense = 0D;
        for(PurchaseOrder purchaseOrder: purchaseOrderList){
            totalExpense += purchaseOrder.getPrice();
        }
        return totalExpense;
    }
}
