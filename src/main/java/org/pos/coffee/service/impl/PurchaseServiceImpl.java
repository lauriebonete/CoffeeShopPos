package org.pos.coffee.service.impl;

import org.apache.commons.lang.time.DateFormatUtils;
import org.evey.dao.SequenceDao;
import org.evey.service.impl.BaseCrudServiceImpl;
import org.pos.coffee.bean.Item;
import org.pos.coffee.bean.Purchase;
import org.pos.coffee.bean.PurchaseOrder;
import org.pos.coffee.bean.helper.report.PurchaseReportHelper;
import org.pos.coffee.dao.PurchaseDao;
import org.pos.coffee.dao.PurchaseDaoJdbc;
import org.pos.coffee.service.ItemService;
import org.pos.coffee.service.PurchaseOrderService;
import org.pos.coffee.service.PurchaseService;
import org.pos.coffee.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Laurie on 1/4/2016.
 */
@Service("purchaseService")
public class PurchaseServiceImpl extends BaseCrudServiceImpl<Purchase> implements PurchaseService {

    @Autowired
    private SequenceDao sequenceDao;

    @Autowired
    private PurchaseDaoJdbc purchaseDaoJdbc;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private StockService stockService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private PurchaseDao purchaseDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Purchase savePurchaseAndPO(Purchase purchase) {

        purchase.setTotalExpense(purchaseOrderService.countTotalExpense(purchase.getPurchaseOrders()));
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
        loadPurchase.setReceiveDate(new Date());
        loadPurchase.setTotalExpense(purchaseOrderService.recountTotalExpense(loadPurchase.getPurchaseOrders()));
        purchaseDao.save(loadPurchase);
        return loadPurchase;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Purchase createPurchase(Purchase purchase) {
        purchase.setPurchaseCode(this.generatePurchaseCode("PO_",1,1,5));
        purchase.setStatus(Purchase.Status.FOR_APPROVAL.getValue());
        purchase.setRequestDate(new Date());

        Double totalPrice = 0D;
        for(PurchaseOrder purchaseOrder: purchase.getPurchaseOrders()){
            Item item = itemService.load(purchaseOrder.getOrderedItem().getId());

            purchaseOrder.setPurchase(purchase);
            purchaseOrder.setPrice(item.getUnitPrice()*purchaseOrder.getOrderedQuantity());
            purchaseOrderService.save(purchaseOrder);

            totalPrice += purchaseOrder.getPrice();
        }
        purchase.setTotalExpense(totalPrice);
        this.save(purchase);
        return purchase;
    }

    @Override
    @Transactional
    public String generatePurchaseCode(String key, int increment, int retryCount, int maxRetry) {
        final String datePrefix = DateFormatUtils.format(Calendar.getInstance().getTime(), "yyyyMMdd");
        Long generatedCode = sequenceDao.incrementValue(key+datePrefix, increment, retryCount, maxRetry);
        return key+datePrefix+String.format("%06d",generatedCode);
    }

    @Override
    public List<PurchaseReportHelper> getPurchaseUsingDateRange(Date startDate, Date endDate) {
        return purchaseDaoJdbc.getPurchaseUsingDateRange(startDate,endDate);
    }
}
