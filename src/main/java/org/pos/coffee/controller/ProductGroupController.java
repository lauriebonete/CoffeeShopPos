package org.pos.coffee.controller;

import org.evey.controller.BaseCrudController;
import org.pos.coffee.bean.Product;
import org.pos.coffee.bean.ProductGroup;
import org.pos.coffee.service.ProductGroupService;
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
 * Created by Laurie on 12/16/2015.
 */
@Controller
@RequestMapping("/productGroup")
public class ProductGroupController extends BaseCrudController<ProductGroup> {

    @Autowired
    private ProductGroupService productGroupService;

    @RequestMapping(value = "/saveImage", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    Map<String,Object> saveProductImage(@RequestBody ProductGroup productGroup){
        Map<String, Object> returnMap = new HashMap<>();

        ProductGroup saveToThis = productGroupService.load(productGroup.getId());
        saveToThis.setGroupImage(productGroup.getGroupImage());
        productGroupService.save(saveToThis);

        returnMap.put("status", true);
        returnMap.put("message", "success");
        returnMap.put("result", saveToThis);

        return returnMap;
    }

    @RequestMapping("/getProductGroups")
    public @ResponseBody
    List<ProductGroup> getProductGroup(){
        List<ProductGroup> productGroupList = new ArrayList<>();
        productGroupList = productGroupService.getProductGroups();
        return productGroupList;
    }
}
