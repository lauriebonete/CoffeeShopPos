package org.evey.dao.impl;

import org.evey.bean.UserRole;
import org.evey.dao.UserRoleDao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
 * Created by Laurie on 2/18/2016.
 */
@Repository("userRoleDao")
public class UserRoleDaoJpaImpl extends BaseEntityDaoJpaImpl<UserRole,Long> implements UserRoleDao {
    @Override
    public Boolean checkIfUserRoleIsUnique(String roleName) {
        String queryString = "SELECT COUNT(obj.id) FROM UserRole obj where obj.roleName = :roleName";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter("roleName", roleName);

        Long count = (Long) query.getSingleResult();
        if(count!=null &&
                count>0){
            return false;
        }

        return true;
    }
}
