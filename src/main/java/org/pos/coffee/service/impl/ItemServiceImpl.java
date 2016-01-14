package org.pos.coffee.service.impl;

import org.evey.service.impl.BaseCrudServiceImpl;
import org.pos.coffee.bean.Item;
import org.pos.coffee.bean.Stock;
import org.pos.coffee.bean.helper.ItemUsedHelper;
import org.pos.coffee.bean.helper.OrderExpenseHelper;
import org.pos.coffee.service.ItemService;
import org.pos.coffee.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Laurie on 11/16/2015.
 */
@Service("itemService")
public class ItemServiceImpl extends BaseCrudServiceImpl<Item> implements ItemService {

    @Autowired
    private StockService stockService;

    @Override
    public List<OrderExpenseHelper> deductItemInventory(List<ItemUsedHelper> itemUsedHelperList) throws Exception{

        List<OrderExpenseHelper> orderExpenseHelpers = new ArrayList<>();
        for(ItemUsedHelper itemUsedHelper: itemUsedHelperList){
            OrderExpenseHelper orderExpenseHelper = new OrderExpenseHelper();
            orderExpenseHelper.setOrderId(itemUsedHelper.getOrderId());

            Double totalExpense = 0D;
            if(itemUsedHelper.getItemUsedAndQuantity()!=null){
                for(Map.Entry<Long,Double> entry: itemUsedHelper.getItemUsedAndQuantity().entrySet()){
                    Long itemId = entry.getKey();
                    Double deductAmount = entry.getValue();

                    Item item = new Item();
                    item.setId(itemId);

                    Stock stock = new Stock();
                    stock.setItem(item);

                    List<Stock> stockList = stockService.findActiveEntity(stock);

                    for(Stock foundStock: stockList){
                        if(foundStock.getQuantity()!=null){
                            if(foundStock.getQuantity()>=deductAmount){
                                foundStock.setQuantity(foundStock.getQuantity()-deductAmount);
                                totalExpense += deductAmount * foundStock.getPrice();
                                if(foundStock.getQuantity()<=0){
                                    foundStock.setIsActive(false);
                                }
                                deductAmount = 0D;
                            } else {
                                deductAmount -= foundStock.getQuantity();
                                totalExpense += foundStock.getQuantity() * foundStock.getPrice();
                                foundStock.setQuantity(0D);
                                foundStock.setIsActive(false);
                            }
                            if(deductAmount<=0){
                                break;
                            }
                        } else {
                            foundStock.setIsActive(false);
                        }
                        stockService.save(foundStock);
                    }
                }
            }
            orderExpenseHelper.setExpense(totalExpense);
            orderExpenseHelpers.add(orderExpenseHelper);
        }
        return orderExpenseHelpers;
    }
}
