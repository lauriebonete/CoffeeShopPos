package org.evey.dao;

import org.evey.bean.UserRole;

/**
 * Created by Laurie on 2/18/2016.
 */
public interface UserRoleDao extends BaseEntityDao<UserRole,Long> {
    public Boolean checkIfUserRoleIsUnique(String roleName);
}
