package org.pos.coffee.service.impl;

import org.apache.log4j.Logger;
import org.evey.security.SessionUser;
import org.evey.service.impl.BaseCrudServiceImpl;
import org.evey.utility.SecurityUtil;
import org.evey.bean.User;
import org.pos.coffee.dao.UserDao;
import org.pos.coffee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kenji on 12/4/2015.
 */
@Service("userService")
public class UserServiceImpl extends BaseCrudServiceImpl<User> implements UserService {

    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User getCurrentUser() {
        try{
            SessionUser sessionUser = SecurityUtil.getSessionUser();
            if(sessionUser!=null){
                User user = userDao.loadUserByUsername(sessionUser.getUsername());
                return user;
            }
            return null;
        } catch (Exception e){
            logger.warn(e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean checkIfPinIsUnique(String pin) {
        return userDao.checkIfPinIsUnique(pin, null);
    }

    @Override
    public Boolean checkIfUsernameIsUnique(String username) {
        return userDao.checkIfUsernameIsUnique(username, null);
    }

    @Override
    public Boolean checkIfPinIsUnique(String pin, Long userId) {
        return userDao.checkIfPinIsUnique(pin, userId);
    }

    @Override
    public Boolean checkIfUsernameIsUnique(String username, Long userId) {
        return userDao.checkIfUsernameIsUnique(username, userId);
    }
}