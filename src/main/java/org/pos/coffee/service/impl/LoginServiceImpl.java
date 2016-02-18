package org.pos.coffee.service.impl;

import org.evey.security.SessionUser;
import org.evey.bean.User;
import org.evey.bean.UserRole;
import org.pos.coffee.service.LoginService;
import org.pos.coffee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
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

       /* for(UserRole userRole : user.getUserRole()){
            authorities.add(new SimpleGrantedAuthority("ROLE_"+userRole.getRole()));
        }*/
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return authorities;
    }

    @Override
    public String getMacAddress() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(ip);

            byte[] mac = networkInterface.getHardwareAddress();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }

            return sb.toString();

        } catch (UnknownHostException | SocketException uhe){
            uhe.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
