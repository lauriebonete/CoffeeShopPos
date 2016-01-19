package org.pos.coffee.controller;

import org.evey.controller.BaseCrudController;
import org.pos.coffee.bean.ListPrice;
import org.pos.coffee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Laurie on 12/1/2015.
 */
@Controller
@RequestMapping("/price")
public class ListPriceController extends BaseCrudController<ListPrice> {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/findAllByPass", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String, Object> findAllByPass() {
        Map<String, Object> map = new HashMap<>();
        List<ListPrice> results = new ArrayList<>();
        results = baseCrudService.findAll();
        for(ListPrice listPrice: results){
            if(listPrice.getProduct()!=null){
                listPrice.setDisplayName(listPrice.getProduct().getProductName());
            } else if(listPrice.getMeal()!=null){
                listPrice.getMeal().getMealName();
            }
        }
        map.put("message", "success");
        map.put("status", true);
        map.put("results", results);
        map.put("size", results.size());
        map.put("listSize", entityListSize);
        return map;
    }
}
