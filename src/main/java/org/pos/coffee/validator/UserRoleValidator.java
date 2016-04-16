package org.pos.coffee.validator;

import org.evey.bean.UserRole;
import org.evey.service.UserRoleService;
import org.evey.validator.BaseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * Created by Laurie on 4/16/2016.
 */
@Component("userRoleValidator")
public class UserRoleValidator extends BaseValidator {

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserRole.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserRole userRole = (UserRole) o;
        if(!userRoleService.checkIfUserRoleIsUnique(userRole.getRoleName())){
            errors.rejectValue("roleName","roleName","Role Name is already taken");
        }
    }
}
