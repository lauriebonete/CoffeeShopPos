package org.pos.coffee.service.impl;

import org.evey.service.impl.BaseCrudServiceImpl;
import org.pos.coffee.bean.PurchaseOrder;
import org.pos.coffee.bean.Stock;
import org.pos.coffee.bean.helper.StockHelper;
import org.pos.coffee.dao.StockDao;
import org.pos.coffee.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Laurie on 1/4/2016.
 */
@Service("stockService")
public class StockServiceImpl extends BaseCrudServiceImpl<Stock> implements StockService {

    @Autowired
    private StockDao stockDao;

    @Override
    public List<StockHelper> getStockCount(String queryName) {
        return stockDao.getStockCount(queryName);
    }

    @Override
    public List<StockHelper> findStockEntity(StockHelper stockHelper) {
        return stockDao.findStockEntity(stockHelper);
    }

    @Override
    public void createInventoryForReceivingPO(List<PurchaseOrder> purchaseOrderList) {
        for(PurchaseOrder purchaseOrder: purchaseOrderList){
            Stock stock = new Stock();
            stock.setItem(purchaseOrder.getOrderedItem());
            stock.setQuantity(purchaseOrder.getReceivedQuantity());
            stock.setPrice(purchaseOrder.getPrice()/purchaseOrder.getReceivedQuantity());
            stockDao.save(stock);
        }
    }
}
