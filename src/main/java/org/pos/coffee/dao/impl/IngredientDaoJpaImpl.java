package org.pos.coffee.dao.impl;

import org.evey.dao.impl.BaseEntityDaoJpaImpl;
import org.pos.coffee.bean.Ingredient;
import org.pos.coffee.dao.IngredientDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Laurie on 11/26/2015.
 */
@Repository("ingredientDao")
public class IngredientDaoJpaImpl extends BaseEntityDaoJpaImpl<Ingredient,Long> implements IngredientDao {
}
