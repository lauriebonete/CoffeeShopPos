package org.pos.coffee.controller;

import org.evey.controller.BaseCrudController;
import org.pos.coffee.bean.FileDetail;
import org.pos.coffee.bean.Product;
import org.pos.coffee.bean.ProductGroup;
import org.pos.coffee.service.FileDetailService;
import org.pos.coffee.service.ProductGroupService;
import org.pos.coffee.service.ProductService;
import org.pos.coffee.service.UserService;
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
 * Created by Laurie on 11/16/2015.
 */
@Controller
@RequestMapping("/product")
public class ProductController extends BaseCrudController<Product> {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileDetailService fileDetailService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/ingredient", method = RequestMethod.POST, produces = "application/json")
    public void addIngredients(@RequestBody Product product){
        Product addedToThis = productService.load(product.getId());
        addedToThis.setIngredientList(product.getIngredientList());
        productService.save(addedToThis);
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody void test(Long id){
    }

    @RequestMapping(value = "/saveImage", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody Map<String,Object> saveProductImage(@RequestBody Product product){
        Map<String, Object> returnMap = new HashMap<>();

        Product saveToThis = productService.load(product.getId());
        FileDetail productImage = fileDetailService.load(product.getProductImage().getId());
        saveToThis.setProductImage(productImage);
        productService.save(saveToThis);

        returnMap.put("status", true);
        returnMap.put("message", "success");
        returnMap.put("result", saveToThis);

        return returnMap;
    }

    @RequestMapping(value = "/findParent", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Product findParent(Long id) {
        Product product = productService.load(id);
        return product;
    }

}
