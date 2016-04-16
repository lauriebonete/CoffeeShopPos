package org.evey.service.impl;

import org.evey.bean.UserRole;
import org.evey.dao.UserRoleDao;
import org.evey.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Laurie on 2/18/2016.
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends BaseCrudServiceImpl<UserRole> implements UserRoleService {

    @Resource(name="authorities")
    private Map<String, String> authorities;

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public Map<String, String> getAllDeclaredAuthorites() {
        return this.authorities;
    }

    @Override
    public Boolean checkIfUserRoleIsUnique(String roleName) {
        return userRoleDao.checkIfUserRoleIsUnique(roleName);
    }
}
