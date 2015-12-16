package org.pos.coffee.controller;

import org.evey.controller.BaseCrudController;
import org.pos.coffee.bean.Sale;
import org.pos.coffee.service.ItemService;
import org.pos.coffee.service.OrderService;
import org.pos.coffee.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by Laurie on 12/14/2015.
 */
@Controller
@RequestMapping("/sale")
public class SaleController extends BaseCrudController<Sale> {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private SaleService saleService;

    @RequestMapping(value = "/confirm", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    void confirmOrder(@RequestBody Sale sale){
        Map<Long, Double> itemUsed = orderService.countUseItems(sale.getOrders());
        itemService.deductItemInventory(itemUsed);

        saleService.countExpensePerOrder(sale);
    }
}
