package org.evey.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by Laurie on 1/19/2016.
 */
public interface UserDetailService {
    public UserDetails loadByUsername(String username);
}
