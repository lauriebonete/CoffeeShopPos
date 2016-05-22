package org.pos.coffee.service.impl;

import org.evey.service.impl.BaseCrudServiceImpl;
import org.pos.coffee.bean.Item;
import org.pos.coffee.bean.Stock;
import org.pos.coffee.bean.helper.ItemUsedHelper;
import org.pos.coffee.bean.helper.OrderExpenseHelper;
import org.pos.coffee.bean.helper.report.ConsumptionHelper;
import org.pos.coffee.dao.ItemDao;
import org.pos.coffee.dao.ItemDaoJdbc;
import org.pos.coffee.service.ItemService;
import org.pos.coffee.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Laurie on 11/16/2015.
 */
@Service("itemService")
public class ItemServiceImpl extends BaseCrudServiceImpl<Item> implements ItemService {

    @Autowired
    private StockService stockService;

    @Autowired
    private ItemDaoJdbc itemDaoJdbc;

    @Autowired
    private ItemDao itemDao;

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

                    for(int i=0; i<=stockList.size()-1;i++){
                        Stock foundStock = stockList.get(i);

                        if(foundStock.getQuantity()!=null) {
                            if(foundStock.getQuantity()>=deductAmount){
                                foundStock.setQuantity(foundStock.getQuantity()-deductAmount);
                                totalExpense += deductAmount * foundStock.getPrice();
                                if(foundStock.getQuantity()<=0){
                                    foundStock.setIsActive(false);
                                }
                                deductAmount = 0D;
                            } else if(foundStock.getQuantity()>0) {
                                deductAmount -= foundStock.getQuantity();
                                totalExpense += foundStock.getQuantity() * foundStock.getPrice();
                                if(i==stockList.size()-1){
                                    foundStock.setQuantity(0-deductAmount);
                                    deductAmount = 0D;
                                } else {
                                    foundStock.setQuantity(0D);
                                    foundStock.setIsActive(false);
                                }
                            } else {
                                //if the foundStock is already negative and there are still foundStock on the list, don't do anything
                                //let the loop continue
                                if(i==stockList.size()-1){
                                    foundStock.setQuantity(foundStock.getQuantity()-deductAmount);
                                    deductAmount = 0D;
                                }
                            }
                            if(deductAmount<=0){
                                break;
                            }
                        } else {
                            if(i==stockList.size()-1){
                                foundStock.setQuantity(0-deductAmount);
                                deductAmount = 0D;
                            } else {
                                foundStock.setIsActive(false);
                            }
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

    @Override
    public List<ConsumptionHelper> oountConsumedItem(Date startDate, Date endDate) {
        return itemDaoJdbc.oountConsumedItem(startDate,endDate);
    }

    @Override
    public Boolean validateItemCode(String itemCode) {
        return validateItemCode(itemCode,null);
    }

    @Override
    public Boolean validateItemCode(String itemCode, Long id) {
        return itemDao.validateItemCode(itemCode,id);
    }
}
