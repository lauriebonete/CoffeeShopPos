package org.pos.coffee.controller;

import org.pos.coffee.bean.Product;
import org.pos.coffee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Laurie on 11/16/2015.
 */
@Controller
@RequestMapping("/product")
public class ProductController extends BaseCrudController<Product> {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/ingredient", method = RequestMethod.POST, produces = "application/json")
    public void addIngredients(@RequestBody Product product){
        Product addedToThis = productService.load(product.getId());
        addedToThis.setIngredientList(product.getIngredientList());
        productService.save(addedToThis);
    }

}
