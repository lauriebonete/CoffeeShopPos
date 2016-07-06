package org.pos.coffee.controller;

import org.evey.controller.BaseCrudController;
import org.pos.coffee.bean.Item;
import org.pos.coffee.bean.Stock;
import org.pos.coffee.service.ItemService;
import org.evey.service.ReferenceLookUpService;
import org.pos.coffee.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laurie on 11/16/2015.
 */
@Controller
@RequestMapping("/item")
public class ItemController extends BaseCrudController<Item> {

    @Autowired
    private ItemService itemService;

    @Autowired
    private StockService stockService;

    @Autowired
    private ReferenceLookUpService referenceLookUpService;

    @RequestMapping(value = "/getAllBypassLazy", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Item> getAllItems(){
       List<Item> itemList =  itemService.findAll();
        for(Item item:itemList){
            item.setUom(referenceLookUpService.load(item.getUom().getId()));
        }
        return itemList;
    }

    @RequestMapping(value = "/load-base", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Item> getAllActiveBases() throws Exception{
        List<Item> resultList = new ArrayList<>();
        Item item =  new Item();
        item.setIsActive(true);
        item.setIsBase(true);
        resultList = itemService.findEntity(item);

        return  resultList;
    }

    @Override
    protected void postCreate(Item command) {
        Stock stock = new Stock();
        stock.setItem(command);
        stock.setIsActive(true);
        stock.setPrice(command.getUnitPrice());
        stockService.save(stock);
    }
}
