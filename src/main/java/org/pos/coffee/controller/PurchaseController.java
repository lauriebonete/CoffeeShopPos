package org.pos.coffee.controller;

import org.evey.controller.BaseCrudController;
import org.pos.coffee.bean.Purchase;
import org.pos.coffee.bean.PurchaseOrder;
import org.pos.coffee.service.PurchaseOrderService;
import org.pos.coffee.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Laurie on 1/4/2016.
 */
@Controller
@RequestMapping("/purchase")
public class PurchaseController extends BaseCrudController<Purchase> {

    @Autowired
    private PurchaseService purchaseService;

    @RequestMapping(value = "/inventory-order", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Map<String,Object> restockThroughInventoryOrder(@RequestBody Purchase purchase){
        Map<String,Object> returnMap = new HashMap<>();
        purchase.setStatus(Purchase.Status.FOR_APPROVAL.getValue());
        Purchase purchaseSaved = purchaseService.savePurchaseAndPO(purchase);

        returnMap.put("result", purchaseSaved);
        returnMap.put("status", true);

        return returnMap;
    }

    @RequestMapping(value = "/inventory-purchase", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Map<String,Object> restockThroughInventoryPurchase(@RequestBody Purchase purchase){
        Map<String,Object> returnMap = new HashMap<>();
        purchase.setStatus(Purchase.Status.IN_TRANSIT.getValue());
        Purchase purchaseSaved = purchaseService.savePurchaseAndPO(purchase);

        returnMap.put("result", purchaseSaved);
        returnMap.put("status", true);

        return returnMap;
    }
}
