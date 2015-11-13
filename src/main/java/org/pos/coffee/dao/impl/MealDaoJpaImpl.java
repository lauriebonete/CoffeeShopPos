package org.pos.coffee.dao.impl;

import org.pos.coffee.bean.Meal;
import org.pos.coffee.dao.MealDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Laurie on 11/11/2015.
 */
@Repository("mealDao")
public class MealDaoJpaImpl extends BaseEntityDaoJpaImpl<Meal,Long> implements MealDao {
}
