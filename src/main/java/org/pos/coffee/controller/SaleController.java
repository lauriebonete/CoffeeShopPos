package org.pos.coffee.controller;

import org.evey.controller.BaseCrudController;
import org.pos.coffee.service.ReceiptPDFService;
import org.pos.coffee.bean.Order;
import org.pos.coffee.bean.Sale;
import org.pos.coffee.service.BranchService;
import org.pos.coffee.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Laurie on 12/14/2015.
 */
@Controller
@RequestMapping("/sale")
public class SaleController extends BaseCrudController<Sale> {

    @Autowired
    private SaleService saleService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private ReceiptPDFService receiptPDFService;

    @RequestMapping(value = "/confirm", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Map<String,Object> confirmOrder(@RequestBody Sale sale) throws Exception{
        Map<String, Object> returnMap = new HashMap<>();
        Sale saleCreated = saleService.confirmSaleTransaction(sale);

        returnMap.put("result", saleCreated);
        returnMap.put("status", true);
        returnMap.put("message", "Order successfully saved.");

        return returnMap;
    }

    @RequestMapping(value = "/display-summary", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String,Object> displaySummary(Long id){
        Map<String,Object> returnMap = new HashMap<>();
        Sale sale = saleService.load(id);
        for(Order order: sale.getOrders()){
            //bypass lazy
            order.getId();
        }

        returnMap.put("result", sale);
        returnMap.put("success", true);

        return returnMap;
    }

    @RequestMapping(value = "/reserve-sale-code", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String reserveSaleCode(){
        return saleService.generatePurchaseCode("SALE_CODE",1,1,5);
    }
}
