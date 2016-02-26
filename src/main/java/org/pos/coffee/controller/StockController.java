package org.pos.coffee.controller;

import org.evey.controller.BaseCrudController;
import org.pos.coffee.bean.Item;
import org.pos.coffee.bean.Stock;
import org.pos.coffee.bean.helper.StockHelper;
import org.pos.coffee.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Laurie on 1/4/2016.
 */
@Controller
@RequestMapping("/inventory")
public class StockController extends BaseCrudController<Stock> {

    @Autowired
    private StockService stockService;

    @RequestMapping(value = "/stockCount", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String,Object> getStockCount(){
        List<StockHelper> stockList = stockService.getStockCount("jpql.inventory.display-inventory-count");

        if(stockList == null){
            stockList = new ArrayList<>();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", "success");
        map.put("status", true);
        map.put("results", stockList);
        map.put("size", stockList.size());
        map.put("listSize", entityListSize);
        return map;
    }

    @RequestMapping(value = "/findStock", method = RequestMethod.POST, produces = "application/json")
    public final @ResponseBody Map<String, Object> findStock(@RequestBody StockHelper entity) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<StockHelper> results = stockService.findStockEntity(entity);

        map.put("message", "success");
        map.put("status", true);
        map.put("results", results);
        map.put("size", results.size());
        map.put("listSize", entityListSize);
        return map;
    }

    @RequestMapping(value = "/rebalance", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Map<String, Object> rebalance(Long itemId, Double currentQty, Double correctQty) throws Exception{
        Map<String,Object> returnMap = new HashMap<>();

        Item item = new Item();
        item.setId(itemId);

        Stock stock = new Stock();
        stock.setItem(item);
        List<Stock> stockList = stockService.findActiveEntity(stock);

        if(correctQty>currentQty){
            for(Stock foundStock : stockList){
                if(foundStock.getQuantity()!=null){
                    foundStock.setQuantity(foundStock.getQuantity()+(correctQty-currentQty));
                } else {
                    foundStock.setQuantity(correctQty-currentQty);
                }
                stockService.save(foundStock);
                break;
            }
        } else {
            Double discrepancy = currentQty - correctQty;
            for(Stock foundStock: stockList){
                //substract qty
                if(foundStock.getQuantity()>discrepancy){
                    foundStock.setQuantity(foundStock.getQuantity()-(discrepancy));
                    stockService.save(foundStock);
                    break;
                } else {
                    discrepancy -= foundStock.getQuantity();
                    foundStock.setQuantity(0D);
                    foundStock.setIsActive(false);
                }

                stockService.save(foundStock);
                if(discrepancy>0){
                    break;
                }
            }
        }

        StockHelper stockHelper = new StockHelper();
        stockHelper.setItem(item);
        List<StockHelper> results = stockService.findStockEntity(stockHelper);
        returnMap.put("results",results!=null ? results.get(0):null);
        returnMap.put("success",true);
        return returnMap;
    }

}
