package org.pos.coffee.controller;

import org.evey.controller.BaseCrudController;
import org.pos.coffee.bean.*;
import org.pos.coffee.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by Laurie on 11/14/2015.
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseCrudController<Order> {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileDetailService fileDetailService;

    @Autowired
    private ReferenceLookUpService referenceLookUpService;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private ListPriceService listPriceService;

    @Autowired
    private PriceSetService priceSetService;

    @Autowired
    private OrderService orderService;


    @RequestMapping(value = "/getAllProduct", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Product> getAllProductDetails() throws Exception{


        Product getAll = new Product();
        getAll.setIsActive(true);
        getAll.setIsDisplayOnOrder(true);
        List<Product> results = productService.findEntity(getAll);
        for(Product product: results){

            if(product.getProductGroupList()!=null){
                for(ReferenceLookUp group: product.getProductGroupList()) {
                    group = referenceLookUpService.load(group.getId());
                }
            }

            if(product.getPromoGroupList() != null){
                for(ReferenceLookUp group: product.getProductGroupList()) {
                    group = referenceLookUpService.load(group.getId());
                }
            }

            if(product.getProductUnder() != null){
                for(Product productUnder: product.getProductUnder()){
                    productUnder = productService.load(productUnder.getId());
                    if(productUnder.getSizeId()!=null){
                        productUnder.setSize(referenceLookUpService.load(productUnder.getSizeId()));
                    }

                    if(productUnder.getPrice()!=null){
                        productUnder.setPrice(listPriceService.load(productUnder.getPrice().getId()));
                    }
                }
            }

            if(product.getPrice()!=null){
                product.setPrice(listPriceService.load(product.getPrice().getId()));
            }

        }

        return results;
    }

    @RequestMapping(value = "/getPriceSet", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Map<String,Object> getPriceSetTriggered(@RequestBody OrderHelper[] orderHelperList) throws Exception{

        List<Order> orderList = new ArrayList<>();
        double total = 0;
        double totalDiscount = 0;
        double totalSurcharge = 0;
        for(OrderHelper orderHelper: orderHelperList){
            get_log().warn(orderHelper.getProductId()+" productId");
            List<PriceSet> priceSetList = priceSetService.getPossiblePriceSets(orderHelper);
            List<PriceSet> applyPriceSetList = priceSetService.getApplicablePriceSets(priceSetList, orderHelper);

            double subtotal = orderHelper.getQuantity() * orderHelper.getPrice();
            double gross = subtotal;
            double totalLineDiscount = 0;
            double totalLineSurcharge = 0;
            for(PriceSet priceSet: applyPriceSetList) {
                get_log().warn(subtotal+" prior priceset");
                if(priceSet.getIsDiscount()){
                    double discount = 0;
                    if(priceSet.getIsPercentage()){
                        discount = subtotal * (priceSet.getPriceSetModifier()/100);
                        gross -= discount;
                    } else {
                        discount = priceSet.getPriceSetModifier();
                        gross -= discount;
                    }
                    totalLineDiscount += discount;
                    totalDiscount += discount;
                } else {
                    double surcharge = 0;
                    if(priceSet.getIsPercentage()){
                        surcharge = subtotal * (priceSet.getPriceSetModifier()/100);
                        gross += surcharge;
                    } else {
                        surcharge = priceSet.getPriceSetModifier();
                        gross += surcharge;
                    }
                    totalLineSurcharge += surcharge;
                    totalSurcharge += surcharge;
                }
                get_log().warn(subtotal+" after price set");
            }
            Order orderLine = new Order();
            orderLine.setProduct(productService.load(orderHelper.getProductId()));
            orderLine.setQuantity(orderHelper.getQuantity());
            orderLine.setTotalLinePrice(subtotal);
            orderLine.setAppliedPriceSet(applyPriceSetList);
            orderLine.setListPrice(listPriceService.load(orderHelper.getListId()));
            orderLine.setTotalPriceSetDisc(totalLineDiscount);
            orderLine.setTotalPriceSetSur(totalLineSurcharge);
            orderLine.setGrossLinePrice(gross);

            orderList.add(orderLine);
            total += gross;
        }


        List<PriceSet> saleLevelPriceSet = priceSetService.getPossibleSalePriceSets();
        Sale sale = new Sale();
        if(saleLevelPriceSet != null){
            sale = priceSetService.applyPriceSets(saleLevelPriceSet, total, totalDiscount, totalSurcharge);
        } else {
            sale.setTotalSale(total);
            sale.setTotalDiscount(totalDiscount);
            sale.setTotalSurcharge(totalSurcharge);
        }
        sale.setOrders(orderList);

        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("results", sale);
        returnMap.put("message", "success");
        returnMap.put("status", true);

        return returnMap;
    }


}
