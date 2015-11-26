package org.pos.coffee.controller;

import org.pos.coffee.bean.Ingredient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Laurie on 11/26/2015.
 */
@Controller
@RequestMapping("/ingredient")
public class IngredientController extends BaseCrudController<Ingredient> {
}
