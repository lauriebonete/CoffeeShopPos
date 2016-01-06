package org.pos.coffee.controller;

import org.evey.controller.BaseCrudController;
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

}
