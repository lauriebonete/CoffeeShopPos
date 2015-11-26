package org.pos.coffee.service.impl;

import org.pos.coffee.bean.Ingredient;
import org.pos.coffee.service.IngredientService;
import org.springframework.stereotype.Service;

/**
 * Created by Laurie on 11/26/2015.
 */
@Service("ingredientService")
public class IngredientServiceImpl extends BaseCrudServiceImpl<Ingredient> implements IngredientService {
}
