package org.pos.coffee.service.impl;

import org.apache.commons.lang.time.DateFormatUtils;
import org.evey.dao.SequenceDao;
import org.evey.service.impl.BaseCrudServiceImpl;
import org.pos.coffee.bean.Purchase;
import org.pos.coffee.bean.PurchaseOrder;
import org.pos.coffee.dao.PurchaseDao;
import org.pos.coffee.service.PurchaseOrderService;
import org.pos.coffee.service.PurchaseService;
import org.pos.coffee.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Laurie on 1/4/2016.
 */
@Service("purchaseService")
public class PurchaseServiceImpl extends BaseCrudServiceImpl<Purchase> implements PurchaseService {

    @Autowired
    private SequenceDao sequenceDao;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private StockService stockService;

    @Autowired
    private PurchaseDao purchaseDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Purchase savePurchaseAndPO(Purchase purchase) {

        purchase.setTotalExpense(purchaseOrderService.countTotalExpense(purchase.getPurchaseOrders()));
        purchase.setPurchaseDate(new Date());
        purchase.setPurchaseCode(this.generatePurchaseCode("PO_",1,1,5));

        this.save(purchase);
        purchaseOrderService.loadItem(purchase.getPurchaseOrders());
        for(PurchaseOrder purchaseOrder: purchase.getPurchaseOrders()){
            purchaseOrder.setPurchase(purchase);
            purchaseOrderService.save(purchaseOrder);
        }

        return purchase;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Purchase receivedPurchaseOrder(Purchase purchase) throws Exception{
        Purchase loadPurchase = this.load(purchase.getId());
        purchaseOrderService.receivePurchaseOrders(loadPurchase.getPurchaseOrders(), purchase.getPurchaseOrders());
        stockService.createInventoryForReceivingPO(loadPurchase.getPurchaseOrders());
        loadPurchase.setStatus(Purchase.Status.RECEIVED.getValue());
        purchaseDao.save(loadPurchase);
        return purchase;
    }

    @Override
    @Transactional
    public String generatePurchaseCode(String key, int increment, int retryCount, int maxRetry) {
        final String datePrefix = DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMdd");
        Long generatedCode = sequenceDao.incrementValue(key+datePrefix, increment, retryCount, maxRetry);
        return key+datePrefix+String.format("%06d",generatedCode);
    }
}
