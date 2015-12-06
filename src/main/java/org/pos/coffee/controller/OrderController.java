package org.pos.coffee.controller;

import org.evey.controller.BaseCrudController;
import org.pos.coffee.bean.*;
import org.pos.coffee.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

            /*ListPrice lookFor = new ListPrice();
            lookFor.setProduct(new Product());
            lookFor.getProduct().setId(product.getId());
            List<ListPrice> prices = listPriceService.findEntity(lookFor);
            product.setPrice(prices);*/

        }

        return results;
    }
}
