package org.pos.coffee.service.impl;

import org.evey.service.impl.BaseCrudServiceImpl;
import org.pos.coffee.bean.User;
import org.pos.coffee.service.UserService;
import org.springframework.stereotype.Service;

/**
 * Created by kenji on 12/4/2015.
 */
@Service("userService")
public class UserServiceImpl extends BaseCrudServiceImpl<User> implements UserService {
}