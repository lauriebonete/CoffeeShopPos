package org.pos.coffee.service.impl;

import org.evey.security.SessionUser;
import org.pos.coffee.bean.User;
import org.pos.coffee.bean.UserRole;
import org.pos.coffee.service.LoginService;
import org.pos.coffee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenji on 12/4/2015.
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String pinDigit = authentication.getName();
        String password = authentication.getCredentials().toString();

        User lookFor = new User();
        lookFor.setPinDigit(pinDigit);

        List<User> results = new ArrayList<>();

        try {
            results= userService.findEntity(lookFor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        User user = null;
        if(!results.isEmpty()){
            user = results.get(0);
        }

        if (user != null) {
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user));
            SessionUser sessionUser = new SessionUser(userDetails);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(sessionUser, null, getGrantedAuthorities(user));
            SecurityContextHolder.getContext().setAuthentication(auth);

            return auth;
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for(UserRole userRole : user.getUserRole()){
            authorities.add(new SimpleGrantedAuthority("ROLE_"+userRole.getRole()));
        }
        System.out.print("authorities :"+authorities);
        return authorities;
    }


}
