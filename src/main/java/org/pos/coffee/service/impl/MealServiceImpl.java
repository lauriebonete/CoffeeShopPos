package org.pos.coffee.service.impl;

import org.pos.coffee.bean.Meal;
import org.pos.coffee.service.MealService;
import org.springframework.stereotype.Service;

/**
 * Created by Laurie on 11/11/2015.
 */
@Service("mealService")
public class MealServiceImpl extends BaseCrudServiceImpl<Meal> implements MealService {
}
