package org.pos.coffee.validator;

import org.evey.bean.User;
import org.evey.validator.BaseValidator;
import org.pos.coffee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * Created by Laurie on 4/14/2016.
 */
@Component("userValidator")
public class UserValidator extends BaseValidator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;



        if(user.isNew()){
            if(!userService.checkIfPinIsUnique(user.getPinDigit())){
                errors.rejectValue("pinDigit","pinDigit","Pin Digit is already taken");
            }

            if(!userService.checkIfUsernameIsUnique(user.getUsername())){
                errors.rejectValue("username","username","Username is already taken");
            }
        } else {
            if(!userService.checkIfPinIsUnique(user.getPinDigit(), user.getId())){
                errors.rejectValue("pinDigit","pinDigit","Pin Digit is already taken");
            }

            if(!userService.checkIfUsernameIsUnique(user.getUsername(), user.getId())){
                errors.rejectValue("username","username","Username is already taken");
            }
        }

        if(user.getUserRole() == null || user.getUserRole().isEmpty()){
            errors.rejectValue("userRole","userRole","User Role is required.");
        }

    }
}
