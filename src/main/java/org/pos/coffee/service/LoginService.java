package org.pos.coffee.service;

import org.springframework.security.core.Authentication;

/**
 * Created by kenji on 12/4/2015.
 */
public interface LoginService {

    public Authentication authenticate(Authentication authentication);

    public boolean supports(Class<?> authentication);

    public String getMacAddress();

}
