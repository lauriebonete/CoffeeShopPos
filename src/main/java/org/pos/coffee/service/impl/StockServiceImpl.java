package org.pos.coffee.service.impl;

import org.evey.service.impl.BaseCrudServiceImpl;
import org.pos.coffee.bean.Item;
import org.pos.coffee.bean.PurchaseOrder;
import org.pos.coffee.bean.Stock;
import org.pos.coffee.bean.helper.StockHelper;
import org.pos.coffee.dao.StockDao;
import org.pos.coffee.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void createInventoryForReceivingPO(List<PurchaseOrder> purchaseOrderList) throws Exception{
        for(PurchaseOrder purchaseOrder: purchaseOrderList){
            if(purchaseOrder.getReceivedQuantity()!=null &&
                    purchaseOrder.getPrice() !=null){
                Stock stock = new Stock();
                stock.setItem(purchaseOrder.getOrderedItem());
                stock.setQuantity(purchaseOrder.getReceivedQuantity());
                stock.setPrice(purchaseOrder.getPrice()/purchaseOrder.getReceivedQuantity());
                stock.setIsActive(true);
                stockDao.save(stock);
            }

            List<Stock> stockList = new ArrayList<>();

            Stock lookFor = new Stock();
            Item item  = new Item();
            item.setId(purchaseOrder.getOrderedItem().getId());
            lookFor.setItem(item);
            lookFor.setQuantity(0D);

            stockList.addAll(stockDao.findEntity(lookFor));

            Map<String,Object> params = new HashMap<>();
            params.put("item", item.getId());

            stockList.addAll(this.findEntityByNamedQuery("jpql.stock.retrieve-null-stocks",params));

            for (Stock inactive: stockList){
                inactive.setIsActive(false);
                stockDao.save(inactive);
            }
        }
    }
}
