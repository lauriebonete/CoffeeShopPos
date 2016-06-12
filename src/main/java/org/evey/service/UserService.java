package org.evey.service;

import org.evey.service.BaseCrudService;
import org.evey.bean.User;

/**
 * Created by kenji on 12/4/2015.
 */
public interface UserService extends BaseCrudService<User> {
    public User getCurrentUser();

    public Boolean checkIfPinIsUnique(String pin);
    public Boolean checkIfUsernameIsUnique(String username);

    public Boolean checkIfPinIsUnique(String pin, Long userId);
    public Boolean checkIfUsernameIsUnique(String username, Long userId);
}
