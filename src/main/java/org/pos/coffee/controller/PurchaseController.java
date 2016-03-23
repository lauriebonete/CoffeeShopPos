package org.pos.coffee.controller;

import org.evey.controller.BaseCrudController;
import org.pos.coffee.bean.Purchase;
import org.pos.coffee.bean.PurchaseOrder;
import org.evey.bean.User;
import org.pos.coffee.service.PurchaseOrderService;
import org.pos.coffee.service.PurchaseService;
import org.pos.coffee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by Laurie on 1/4/2016.
 */
@Controller
@RequestMapping("/purchase")
public class PurchaseController extends BaseCrudController<Purchase> {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private UserService userService;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @RequestMapping(value = "/inventory-order", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Map<String,Object> restockThroughInventoryOrder(@RequestBody Purchase purchase){
        Map<String,Object> returnMap = new HashMap<>();
        purchase.setStatus(Purchase.Status.FOR_APPROVAL.getValue());
        purchase.setRequestDate(new Date());

        User user = userService.getCurrentUser();
        if(user!=null){
            purchase.setCreatedByUsername(user.getUsername());
        }

        Purchase purchaseSaved = purchaseService.savePurchaseAndPO(purchase);

        returnMap.put("result", purchaseSaved);
        returnMap.put("status", true);

        return returnMap;
    }

    @RequestMapping(value = "/inventory-purchase", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Map<String,Object> restockThroughInventoryPurchase(@RequestBody Purchase purchase){
        Map<String,Object> returnMap = new HashMap<>();
        purchase.setStatus(Purchase.Status.IN_PROGRESS.getValue());
        purchase.setPurchaseDate(new Date());
        purchase.setRequestDate(new Date());
        Purchase purchaseSaved = purchaseService.savePurchaseAndPO(purchase);

        returnMap.put("result", purchaseSaved);
        returnMap.put("status", true);

        return returnMap;
    }

    @RequestMapping(value = "/findPO", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<PurchaseOrder> findPO(Long purchaseId){
        List<PurchaseOrder> purchaseOrders = new ArrayList<>();

        Purchase purchase = purchaseService.load(purchaseId);

        return purchase.getPurchaseOrders();
    }

    @RequestMapping(value = "/update-status", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Map<String,Object> updateStatusPurchase(@RequestBody Purchase updatePurchase) throws Exception{
        Purchase purchase = purchaseService.load(updatePurchase.getId());
        purchase.setStatus(Purchase.Status.findByString(updatePurchase.getStatus()).getValue());

        for(PurchaseOrder po: updatePurchase.getPurchaseOrders()){
            for(PurchaseOrder originalPo: purchase.getPurchaseOrders()){
                if(originalPo.getId()==po.getId()){
                    originalPo.setOrderedQuantity(po.getOrderedQuantity());
                    break;
                }
            }
        }

        if ("In Progress".equals(updatePurchase.getStatus())) {
            purchase.setPurchaseDate(new Date());
        }
        purchaseService.save(purchase);

        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("result", purchase);
        returnMap.put("status",true);
        returnMap.put("message","Purchase Order is now in-progress");
        return returnMap;
    }

    @RequestMapping(value = "/receive-purchase", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Map<String, Object> receivePurchase(@RequestBody Purchase purchase) throws Exception{
        Map<String, Object> returnMap = new HashMap<>();
        Purchase updated = purchaseService.receivedPurchaseOrder(purchase);

        returnMap.put("result", updated);
        returnMap.put("status", true);
        returnMap.put("message","Purchase Order is successfully received.");

        return returnMap;
    }

    @RequestMapping(value = "/create-purchase", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Map<String, Object> createPurchase(@RequestBody Purchase purchase) {
        Map<String, Object> returnMap = new HashMap<>();
        Purchase saved = purchaseService.createPurchase(purchase);
        returnMap.put("result", saved);
        returnMap.put("status", true);
        returnMap.put("message", "Purchase is successfully made.");

        return returnMap;
    }
}
