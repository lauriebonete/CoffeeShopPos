package org.pos.coffee.service.impl;

import org.evey.service.impl.BaseCrudServiceImpl;
import org.pos.coffee.bean.*;
import org.pos.coffee.bean.helper.ItemUsedHelper;
import org.pos.coffee.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Laurie on 12/8/2015.
 */
@Service("orderService")
public class OrderServiceImpl extends BaseCrudServiceImpl<Order> implements OrderService {

    @Override
    public List<ItemUsedHelper> countUseItems(List<Order> orderList) {
        List<ItemUsedHelper> itemUsedHelperList = new ArrayList<>();
        for(Order order: orderList){
            ItemUsedHelper itemUsedHelper = new ItemUsedHelper();
            Map<Long,Double> itemUsedMap = new HashMap<>();

            if(order.getQuantity()!= null
                    && order.getQuantity()>0){
                itemUsedHelper.setOrderId(order.getId());

                Product product = order.getProduct();
                List<Ingredient> ingredientList = product.getIngredientList();
                if(ingredientList!=null){
                    for(Ingredient ingredient: ingredientList){

                        if(itemUsedMap.get(ingredient.getItemId())!=null){
                            double quantityUsed = itemUsedMap.get(ingredient.getItemId());
                            quantityUsed += ingredient.getQuantity()*order.getQuantity();
                            itemUsedMap.put(ingredient.getItemId(),quantityUsed);
                        } else {
                            itemUsedMap.put(ingredient.getItemId(),ingredient.getQuantity()*order.getQuantity());
                        }
                    }
                }
                itemUsedHelper.setItemUsedAndQuantity(itemUsedMap);
            }
            itemUsedHelperList.add(itemUsedHelper);
        }

        return itemUsedHelperList;
    }
}
