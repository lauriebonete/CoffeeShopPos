package org.pos.coffee.validator;

import org.evey.validator.BaseValidator;
import org.pos.coffee.bean.ReferenceLookUp;
import org.pos.coffee.service.ReferenceLookUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * Created by Laurie on 5/22/2016.
 */

@Component("referenceLookUpValidator")
public class ReferenceLookUpValidator extends BaseValidator {

    @Autowired
    private ReferenceLookUpService referenceLookUpService;

    @Override
    public boolean supports(Class<?> aClass) {
        return ReferenceLookUp.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ReferenceLookUp referenceLookUp = (ReferenceLookUp) o;

        if(referenceLookUp.isNew()){
            if(!referenceLookUpService.validateUniqueKey(referenceLookUp.getKey())){
                errors.rejectValue("key","key","Key is already taken.");
            }
        } else {
            if(!referenceLookUpService.validateUniqueKey(referenceLookUp.getKey(), referenceLookUp.getId())){
                errors.rejectValue("key","key","Key is already taken.");
            }
        }
    }
}
