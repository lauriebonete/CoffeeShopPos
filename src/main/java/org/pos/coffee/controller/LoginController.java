package org.pos.coffee.controller;

import org.pos.coffee.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by kenji on 12/4/2015.
 */
@Controller
@RequestMapping("/login")
public class LoginController implements AuthenticationProvider {

    @Autowired
    private LoginService loginService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return loginService.authenticate(authentication);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return loginService.supports(aClass);
    }

    @RequestMapping
    public ModelAndView loadHtml() {
        return new ModelAndView("html/login.html");
    }

}



