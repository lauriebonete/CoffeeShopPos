package org.pos.coffee.validator;

import org.evey.validator.BaseValidator;
import org.pos.coffee.bean.Purchase;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * Created by Laurie on 5/22/2016.
 */
@Component("purchaseValidator")
public class PurchaseValidator extends BaseValidator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Purchase.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Purchase purchase = (Purchase) o;

        if(purchase.getPurchaseOrders()==null || purchase.getPurchaseOrders().isEmpty()){
            errors.rejectValue("purchaseOrders","purchaseOrderHolder","Please add at least one Item for purchase.");
        }
    }
}
