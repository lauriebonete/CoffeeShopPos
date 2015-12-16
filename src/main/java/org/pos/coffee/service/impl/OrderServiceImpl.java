package org.pos.coffee.service.impl;

import org.evey.service.impl.BaseCrudServiceImpl;
import org.pos.coffee.bean.*;
import org.pos.coffee.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Laurie on 12/8/2015.
 */
@Service("orderService")
public class OrderServiceImpl extends BaseCrudServiceImpl<Order> implements OrderService {

    @Override
    public Map<Long, Double> countUseItems(List<Order> orderList) {
        Map<Long, Double> itemUsed = new HashMap<>();
        for(Order order: orderList){
            Product product = order.getProduct();
            List<Ingredient> ingredientList = product.getIngredientList();
            if(ingredientList!=null){
                for(Ingredient ingredient: ingredientList){
                    if(itemUsed.get(ingredient.getItemId())!=null){
                        double quantityUsed = itemUsed.get(ingredient.getItemId());
                        quantityUsed += ingredient.getQuantity();
                        itemUsed.put(ingredient.getItemId(),quantityUsed);
                    } else {
                        itemUsed.put(ingredient.getItemId(),ingredient.getQuantity());
                    }
                }
            }
        }

        return itemUsed;
    }
}
