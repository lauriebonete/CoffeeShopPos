package org.evey.dao;

import org.evey.dao.BaseEntityDao;
import org.evey.bean.User;

/**
 * Created by kenji on 12/4/2015.
 */
public interface UserDao extends BaseEntityDao<User,Long> {
    public User loadUserByUsername(String username);
    public Boolean checkIfPinIsUnique(String pin, Long userId);
    public Boolean checkIfUsernameIsUnique(String username, Long userId);
}
