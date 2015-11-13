package org.pos.coffee.controller;

import org.pos.coffee.bean.Meal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Laurie on 11/11/2015.
 */
@Controller
@RequestMapping("/meal")
public class MealController extends BaseCrudController<Meal> {
}
