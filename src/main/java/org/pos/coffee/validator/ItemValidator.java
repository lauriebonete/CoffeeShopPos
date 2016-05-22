package org.pos.coffee.validator;

import org.evey.validator.BaseValidator;
import org.pos.coffee.bean.Item;
import org.pos.coffee.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * Created by Laurie on 5/22/2016.
 */

@Component("itemValidator")
public class ItemValidator extends BaseValidator {

    @Autowired
    private ItemService itemService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Item.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Item item = (Item) o;

        if(item.isNew()){
            if(!itemService.validateItemCode(item.getItemCode())){
                errors.rejectValue("itemCode","itemCode","Item Code is already taken.");
            }
        } else {
            if(!itemService.validateItemCode(item.getItemCode(),item.getId())){
                errors.rejectValue("itemCode","itemCode","Item Code is already taken.");
            }
        }
    }
}
