package org.pos.coffee.validator;

import org.evey.validator.BaseValidator;
import org.pos.coffee.bean.Purchase;
import org.pos.coffee.bean.PurchaseOrder;
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

        if(purchase.getPurchaseOrders()!=null && !purchase.getPurchaseOrders().isEmpty()){
            for(PurchaseOrder po: purchase.getPurchaseOrders()){
                if(purchase.getStatus()==null || (purchase.getStatus()!=null && purchase.getStatus().equals("In Progress"))){
                    if(po.getOrderedQuantity() == null || po.getOrderedQuantity()<=0){
                        errors.rejectValue("purchaseOrders","purchaseOrderHolder","Please review your requested quantity. Quantity should be greater than 0.");
                        break;
                    }
                }
                if(purchase.getStatus()!=null && purchase.getStatus().equals("Received")){
                    if(po.getReceivedQuantity()==null || po.getReceivedQuantity()<=0){
                        errors.rejectValue("purchaseOrders","purchaseOrderHolder","Please review your received quantity. Quantity should be greater than 0.");
                        break;
                    }
                }

            }
        }
    }
}
