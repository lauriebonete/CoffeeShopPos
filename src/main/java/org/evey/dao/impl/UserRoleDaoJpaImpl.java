package org.evey.dao.impl;

import org.evey.bean.UserRole;
import org.evey.dao.UserRoleDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Laurie on 2/18/2016.
 */
@Repository("userRoleDao")
public class UserRoleDaoJpaImpl extends BaseEntityDaoJpaImpl<UserRole,Long> implements UserRoleDao {
}
