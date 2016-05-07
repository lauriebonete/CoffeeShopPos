package org.pos.coffee.validator;

import org.evey.validator.BaseValidator;
import org.pos.coffee.bean.PriceSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * Created by Laurie on 5/7/2016.
 */
@Component("priceSetValidator")
public class PriceSetValidator extends BaseValidator {
    @Override
    public boolean supports(Class<?> aClass) {
        return PriceSet.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PriceSet priceSet = (PriceSet) o;

        if(priceSet.getIsTriggeredByProduct() && priceSet.getProduct()==null){
            errors.rejectValue("product","product","Please provide a valid product.");
        }
        if(priceSet.getIsTriggeredByPromoGroup() && priceSet.getPromoGroup()==null){
            errors.rejectValue("promoGroup","promoGroup","Please provide a valid promo group.");
        }
        if(priceSet.getIsQuantityTriggered() && priceSet.getMinQuantity()==null){
            errors.rejectValue("minQuantity","minQuantity","Please provide a valid quantity.");
        }
        if(priceSet.getIsPriceTriggered() && priceSet.getMinPrice()==null){
            errors.rejectValue("minPrice","minPrice","Please provide a valid price.");
        }
        if(priceSet.getIsVoucherTriggered() && (priceSet.getVoucherCode()==null || priceSet.getVoucherCode().length()<=0)){
            errors.rejectValue("voucherCode","voucherCode","Please provide a valid voucher code.");
        }
        if(priceSet.getIsPercentage() && priceSet.getPriceSetModifier()==null){
            errors.rejectValue("priceSetModifier","priceSetModifier","Please provide a valid modifier.");
        }

    }
}
