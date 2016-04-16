package org.evey.service;

import org.evey.bean.UserRole;

import java.util.Map;

/**
 * Created by Laurie on 2/18/2016.
 */
public interface UserRoleService extends BaseCrudService<UserRole> {
    public Map<String,String> getAllDeclaredAuthorites();
    public Boolean checkIfUserRoleIsUnique(String roleName);
}
