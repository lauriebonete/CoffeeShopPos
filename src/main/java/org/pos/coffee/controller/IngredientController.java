package org.pos.coffee.controller;

import org.evey.controller.BaseCrudController;
import org.pos.coffee.bean.Ingredient;
import org.pos.coffee.service.IngredientService;
import org.pos.coffee.service.ReferenceLookUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laurie on 11/26/2015.
 */
@Controller
@RequestMapping("/ingredient")
public class IngredientController extends BaseCrudController<Ingredient> {

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private ReferenceLookUpService referenceLookUpService;

    @RequestMapping(value = "/getIngredient", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Ingredient> getProductIngredient(Long productId) throws Exception{
        List<Ingredient> results = new ArrayList<>();

        Ingredient lookFor = new Ingredient();
        lookFor.setProductId(productId);
        results = ingredientService.findEntity(lookFor);
        for(Ingredient ingredient:results){
            ingredient.getItem().setUom(referenceLookUpService.load(ingredient.getItem().getUom().getId()));
        }

        return results;
    }
}
