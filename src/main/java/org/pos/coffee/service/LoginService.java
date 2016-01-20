package org.pos.coffee.service;

import org.pos.coffee.bean.User;
import org.pos.coffee.bean.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by kenji on 12/4/2015.
 */
public interface LoginService {

    public Authentication authenticate(Authentication authentication);

    public boolean supports(Class<?> authentication);

    public String getMacAddress();

}
