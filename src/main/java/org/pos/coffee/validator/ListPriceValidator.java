package org.pos.coffee.validator;

import org.evey.validator.BaseValidator;
import org.pos.coffee.bean.ListPrice;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * Created by Laurie on 5/7/2016.
 */
@Component("listPriceValidator")
public class ListPriceValidator extends BaseValidator{
    @Override
    public boolean supports(Class<?> aClass) {
        return ListPrice.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ListPrice listPrice = (ListPrice) o;
        if(listPrice.getProduct()==null){
            errors.rejectValue("product","product","Please provide a valid product.");
        }

    }
}
