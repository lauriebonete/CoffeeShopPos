package org.evey.service.impl;

import org.evey.service.UserDetailService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Created by Laurie on 1/19/2016.
 */
@Service("userDetailService")
public class UserDetailServiceImpl implements UserDetailService {

    @Override
    public UserDetails loadByUsername(String username) {
        return null;
    }
}
