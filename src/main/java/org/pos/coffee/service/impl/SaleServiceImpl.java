package org.pos.coffee.service.impl;

import org.evey.service.impl.BaseCrudServiceImpl;
import org.pos.coffee.bean.*;
import org.pos.coffee.service.SaleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Laurie on 12/14/2015.
 */
@Service("saleService")
public class SaleServiceImpl extends BaseCrudServiceImpl<Sale> implements SaleService {

    @Override
    public void countExpensePerOrder(Sale sale) {
        for(Order order: sale.getOrders()){
            Product product = order.getProduct();
            List<Ingredient> ingredientList = product.getIngredientList();
            if(ingredientList!=null){
                for(Ingredient ingredient: ingredientList){
                    double consumed = ingredient.getQuantity() * order.getQuantity();
                    Item item = ingredient.getItem();
                }
            }
        }
    }
}
