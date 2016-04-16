package org.pos.coffee.service;

import org.evey.service.BaseCrudService;
import org.evey.bean.User;

/**
 * Created by kenji on 12/4/2015.
 */
public interface UserService extends BaseCrudService<User> {
    public User getCurrentUser();
    public Boolean checkIfPinIsUnique(String pin);
    public Boolean checkIfUsernameIsUnique(String username);
}
